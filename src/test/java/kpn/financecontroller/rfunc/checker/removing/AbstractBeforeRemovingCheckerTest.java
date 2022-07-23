package kpn.financecontroller.rfunc.checker.removing;

import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractBeforeRemovingCheckerTest {

    @Test
    void shouldCheckChecking() {
        String value = "value";
        ImmutableResult<List<String>> expectedResult = ImmutableResult.<List<String>>ok(null);

        Result<List<String>> result = new TestChecker().apply(value);
        assertThat(expectedResult).isEqualTo(result);
    }

    private static class TestChecker extends AbstractBeforeRemovingChecker<String> {}
}