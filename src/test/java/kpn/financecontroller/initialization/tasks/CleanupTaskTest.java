package kpn.financecontroller.initialization.tasks;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.converter.aspect.FromAspectConverter;
import kpn.financecontroller.initialization.generators.valued.*;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.tasks.testUtils.TestKeys;
import kpn.financecontroller.initialization.tasks.testUtils.TestManagerCreator;
import kpn.lib.buider.ServiceBuider;
import kpn.lib.exception.DTOException;
import kpn.lib.executor.DefaultExecutorResult;
import kpn.lib.executor.ExecutorResult;
import kpn.lib.executor.deleting.CompletelyDeletingExecutor;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.contexts.DefaultContext;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import support.TestDomain;

import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class CleanupTaskTest {

    private static final Valued<String> KEY = TestKeys.KEY;
    private static final ValuedGenerator<String> VALUED_GENERATOR = new ValuedStringGenerator();
    private static final Function<Context, ResultContextManager> CREATOR = new TestManagerCreator();

    private static Result<Void> expectedResult_ifCleaningFail;
    private static Result<Void> expectedResult;

    @BeforeAll
    static void beforeAll() {
        expectedResult_ifCleaningFail = ImmutableResult.<Void>bFail(VALUED_GENERATOR.generate(KEY, Codes.DB_FAIL_CLEANING))
                .arg(KEY)
                .build();
        expectedResult = ImmutableResult.<Void>builder()
                .success(true)
                .arg(KEY)
                .build();
    }

    @Test
    void shouldCheckExecution_ifCleaningFail() throws DTOException {
        DefaultContext context = new DefaultContext();
        CleanupTask task = new CleanupTask();
        task.setKey(KEY);
        task.setValuedGenerator(VALUED_GENERATOR);
        task.setManagerCreator(CREATOR);
        task.setService(createService(new TestDeleter(false)));

        task.execute(context);

        Result<Void> result = CREATOR.apply(context).get(KEY, Properties.DB_CLEANING_RESULT, Void.class);
        assertThat(expectedResult_ifCleaningFail).isEqualTo(result);
    }

    @Test
    void shouldCheckExecution() {
        DefaultContext context = new DefaultContext();
        CleanupTask task = new CleanupTask();
        task.setKey(KEY);
        task.setValuedGenerator(VALUED_GENERATOR);
        task.setManagerCreator(CREATOR);
        task.setService(createService(new TestDeleter(true)));

        task.execute(context);

        Result<Void> result = CREATOR.apply(context).get(KEY, Properties.DB_CLEANING_RESULT, Void.class);
        assertThat(expectedResult).isEqualTo(result);
    }

    private Service<Long, TestDomain, Predicate, Result<List<TestDomain>>> createService(TestDeleter deleter){
        return new ServiceBuider<Long, TestDomain, Predicate, Result<List<TestDomain>>>(new FromAspectConverter<>())
                .deletingAspectBuilder()
                .executorAll(deleter)
                .and()
                .build();
    }

    @AllArgsConstructor
    public static class TestDeleter implements CompletelyDeletingExecutor<TestDomain> {
        private boolean success;
        @Override
        public ExecutorResult<TestDomain> delete() throws DTOException {
            if (success){
                return new DefaultExecutorResult<>(new TestDomain());
            }
            throw new DTOException(VALUED_GENERATOR.generate(KEY, Codes.DB_FAIL_CLEANING));
        }
    }
}
