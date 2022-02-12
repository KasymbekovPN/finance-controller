package kpn.financecontroller.initialization.save.managers;

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
    private static Result<Void> expectedResultAfterSaving;

    @BeforeAll
    static void beforeAll() {
        expectedResultWhenDeleteBeforeTrue = Result.<Void>builder()
                .success(true)
                .code(DELETE_BEFORE_CODE)
                .build();
        expectedResultAfterSaving = Result.<Void>builder()
                .success(true)
                .code(SAVE_CODE)
                .build();
    }

    @Test
    void shouldCheckTargetClearing_whenCollectorCheckingFail() {
        TestSaveManager manager = new TestSaveManager(ID, createFailTestCollectorChecker());
        Result<Void> result = manager.clearTarget();
        assertThat(result).isNotNull();
        assertThat(result.getSuccess()).isFalse();
    }

    @Test
    void shouldCheckTargetClearing_whenNotNeedDeleteBefore() {
        TestSaveManager manager = new TestSaveManager(ID, createTestCollectorChecker());
        Result<Void> result = manager.clearTarget();
        assertThat(expectedResultWhenDeleteBeforeTrue).isEqualTo(result);
    }

    @Test
    void shouldCheckSaving_whenCollectorCheckingFail() {
        TestSaveManager manager = new TestSaveManager(ID, createFailTestCollectorChecker());
        Result<Void> result = manager.save();
        assertThat(result).isNotNull();
        assertThat(result.getSuccess()).isFalse();
    }

    @Test
    void shouldCheckSaving() {
        TestSaveManager manager = new TestSaveManager(ID, createTestCollectorChecker());
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

    private static class TestSaveManager extends AbstractSaveManager<Long, String> {

        public TestSaveManager(String ID, GroupChecker<LoadDataCollector<?, ?>> collectorChecker) {
            super(ID, collectorChecker);
        }

        @Override
        protected Result<Void> checkCollectors() {
            return collectorChecker.check();
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