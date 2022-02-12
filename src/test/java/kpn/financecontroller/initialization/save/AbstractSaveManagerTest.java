package kpn.financecontroller.initialization.save;

import kpn.financecontroller.checkers.GroupChecker;
import kpn.financecontroller.initialization.collectors.LoadDataCollector;
import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractSaveManagerTest {

    private static final String ID = "id";
    private static final String DELETE_BEFORE_CODE = "delete.before.code";
    private static final String SAVE_CODE = "save.code";

    private static Result<Void> expectedResultWhenDeleteBeforeTrue;
    private static Result<Void> expectedResultWhenDeleteBeforeFalse;
    private static Result<Void> expectedResultAfterSaving;

    @BeforeAll
    static void beforeAll() {
        expectedResultWhenDeleteBeforeTrue = Result.<Void>builder()
                .success(true)
                .code(DELETE_BEFORE_CODE)
                .build();
        expectedResultWhenDeleteBeforeFalse = Result.<Void>builder()
                .success(true)
                .code("saveManager.deleteBefore.disabled")
                .arg(ID)
                .build();
        expectedResultAfterSaving = Result.<Void>builder()
                .success(true)
                .code(SAVE_CODE)
                .build();
    }

    @Test
    void shouldCheckTargetClearing_whenCollectorCheckingFail() {
        TestSaveManager manager = new TestSaveManager(ID, createFailTestCollectorChecker(), false);
        Result<Void> result = manager.clearTarget();
        assertThat(result).isNotNull();
        assertThat(result.getSuccess()).isFalse();
    }

    @Test
    void shouldCheckTargetClearing_whenNotNeedDeleteBefore() {
        TestSaveManager manager = new TestSaveManager(ID, createTestCollectorChecker(), true);
        Result<Void> result = manager.clearTarget();
        assertThat(expectedResultWhenDeleteBeforeTrue).isEqualTo(result);
    }

    @Test
    void shouldCheckTargetClearing_whenNeedDeleteBefore() {
        TestSaveManager manager = new TestSaveManager(ID, createTestCollectorChecker(), false);
        Result<Void> result = manager.clearTarget();
        assertThat(expectedResultWhenDeleteBeforeFalse).isEqualTo(result);
    }

    @Test
    void shouldCheckSaving_whenCollectorCheckingFail() {
        TestSaveManager manager = new TestSaveManager(ID, createFailTestCollectorChecker(), false);
        Result<Void> result = manager.save();
        assertThat(result).isNotNull();
        assertThat(result.getSuccess()).isFalse();
    }

    @Test
    void shouldCheckSaving() {
        TestSaveManager manager = new TestSaveManager(ID, createTestCollectorChecker(), false);
        Result<Void> result = manager.save();
        assertThat(expectedResultAfterSaving).isEqualTo(result);
    }

    private static TestCollectorChecker createFailTestCollectorChecker(){
        TestCollectorChecker checker = Mockito.mock(TestCollectorChecker.class);
            Mockito
                    .when(checker.check())
                    .thenReturn(Result.<Void>builder().success(false).build());
        return checker;
    }

    private static TestCollectorChecker createTestCollectorChecker(){
        TestCollectorChecker checker = Mockito.mock(TestCollectorChecker.class);
        Mockito
                .when(checker.check())
                .thenReturn(Result.<Void>builder().success(true).build());
        return checker;
    }

    abstract private static class TestCollectorChecker implements GroupChecker<LoadDataCollector<?, ?>> {}

    private static class TestSaveManager extends AbstractSaveManager<Long, String>{

        private final boolean needDeleteBefore;

        public TestSaveManager(String ID, GroupChecker<LoadDataCollector<?, ?>> collectorChecker, boolean needDeleteBefore) {
            super(ID, collectorChecker);
            this.needDeleteBefore = needDeleteBefore;
        }

        @Override
        protected Result<Void> checkCollectors() {
            return collectorChecker.check();
        }

        @Override
        protected boolean checkNeedDeleteBefore() {
            return needDeleteBefore;
        }

        @Override
        protected Result<Void> deleteBefore() {
            return Result.<Void>builder().success(true).code(DELETE_BEFORE_CODE).build();
        }

        @Override
        protected Result<Void> saveImpl() {
            return Result.<Void>builder().success(true).code(SAVE_CODE).build();
        }

        @Override
        public void clearCollector() {}
    }
}