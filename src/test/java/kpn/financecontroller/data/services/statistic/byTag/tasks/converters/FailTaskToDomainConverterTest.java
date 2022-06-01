package kpn.financecontroller.data.services.statistic.byTag.tasks.converters;

import kpn.financecontroller.data.domains.Domain;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.Task;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Queue;

class FailTaskToDomainConverterTest {

    private static final boolean SUCCESS = true;
    private static final String CODE = "some.code";
    private static final Object[] ARGS = new Object[]{1, 2, 3};

    @Test
    void shouldCheckConversion() {
        Result<TestTask> testTaskResult = this.<TestTask>createResult();
        Result<TestDomain> expectedResult = this.<TestDomain>createResult();

        Result<TestDomain> result = new FailTaskToDomainConverter<TestTask, TestDomain>().apply(testTaskResult);
        Assertions.assertThat(expectedResult).isEqualTo(result);
    }

    private <T> Result<T> createResult() {
        ImmutableResult.Builder<T> builder = ImmutableResult.<T>builder()
                .success(SUCCESS)
                .code(CODE);
        Arrays.stream(ARGS).forEach(builder::arg);
        return builder.build();
    }

    private static class TestTask implements Task{}

    private static class TestDomain implements Domain {
        @Override
        public String getInfo() {return null;}

        @Override
        public String get(Queue<String> path) {return null;}
    }
}