package kpn.financecontroller.data.services.action;

import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ActionWorkerOldImplTest {
    private static final String HEADER = """
            int x = 123;
            int y = 234;
            """;
    private static final String ALGORITHM = """
            Integer r = x + y;
            r
            """;

    @Test
    void shouldCheckExecution_ifCompilationFail() {
        Result<Object> result = new ActionWorkerOldImpl("").execute("123 / 0;");

        ImmutableResult<Object> expectedResult = ImmutableResult.<Object>bFail("gui.text.algorithm.execution.fail")
                .arg("ArithmeticException")
                .arg("Division by zero")
                .build();
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckExecution() {
        ActionWorkerOldImpl worker = new ActionWorkerOldImpl(HEADER);
        Object result = worker.execute(ALGORITHM);

        ImmutableResult<Object> expectedResult = ImmutableResult.<Object>ok(123 + 234);
        assertThat(result).isEqualTo(expectedResult);
    }
}