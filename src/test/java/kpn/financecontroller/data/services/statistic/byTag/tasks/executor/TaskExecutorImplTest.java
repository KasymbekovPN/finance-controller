package kpn.financecontroller.data.services.statistic.byTag.tasks.executor;

import kpn.financecontroller.data.domains.AbstractDomain;
import kpn.financecontroller.data.services.statistic.byTag.tasks.checker.Checker;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.Task;
import kpn.financecontroller.data.services.statistic.byTag.tasks.worker.Worker;
import kpn.financecontroller.rfunc.RRFunction;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class TaskExecutorImplTest {

    private static final String FAIL_CODE = "fail.code";

    private static ImmutableResult<List<TestDomain>> converterResult;
    private static ImmutableResult<TestTask> failCheckerResult;
    private static ImmutableResult<List<TestDomain>> expectedResult;

    @BeforeAll
    static void beforeAll() {
        converterResult = ImmutableResult.<List<TestDomain>>fail(FAIL_CODE);
        failCheckerResult = ImmutableResult.<TestTask>fail(FAIL_CODE);
        expectedResult = ImmutableResult.<List<TestDomain>>ok(new ArrayList<>());
    }

    @Test
    void shouldCheck_whenTaskCheckingFail() {
        TaskExecutorImpl<TestTask, TestDomain> executor
                = new TaskExecutorImpl<TestTask, TestDomain>(createFailChecker(), createTestConverter(), null);

        Result<List<TestDomain>> result = executor.execute(new TestTask());
        assertThat(converterResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenCheckingSuccess() {
        TaskExecutorImpl<TestTask, TestDomain> executor
                = new TaskExecutorImpl<TestTask, TestDomain>(createChecker(), createTestConverter(), createWorker());
        Result<List<TestDomain>> result = executor.execute(new TestTask());
        assertThat(expectedResult).isEqualTo(result);
    }

    private RRFunction<TestTask, List<TestDomain>> createTestConverter() {
        TestConverter converter = Mockito.mock(TestConverter.class);

        Mockito
                .when(converter.apply(Mockito.anyObject()))
                .thenReturn(converterResult);

        return converter;
    }

    private Checker<TestTask> createFailChecker() {
        TestChecker checker = Mockito.mock(TestChecker.class);

        Mockito
                .when(checker.check(Mockito.anyObject()))
                .thenReturn(failCheckerResult);

        return checker;
    }

    private Checker<TestTask> createChecker() {
        TestChecker checker = Mockito.mock(TestChecker.class);

        Mockito
                .when(checker.check(Mockito.anyObject()))
                .thenReturn(ImmutableResult.<TestTask>ok(null));

        return checker;
    }

    private Worker<TestTask, TestDomain> createWorker() {
        TestWorker worker = Mockito.mock(TestWorker.class);

        Mockito
                .when(worker.execute(Mockito.anyObject()))
                .thenReturn(expectedResult);

        return worker;
    }

    abstract private static class TestConverter implements RRFunction<TestTask, List<TestDomain>>{}
    abstract private static class TestChecker implements Checker<TestTask>{}
    abstract private static class TestWorker implements Worker<TestTask, TestDomain> {}

    private static class TestTask implements Task {
    }

    private static class TestDomain extends AbstractDomain {
        public TestDomain(long id) {
            this.id = id;
        }

        @Override
        protected Map<String, Function<GetterArg, String>> takeGetters() {return null;}

        @Override
        public String getInfo() {return null;}
    }
}