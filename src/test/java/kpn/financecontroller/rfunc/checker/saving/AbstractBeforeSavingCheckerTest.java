package kpn.financecontroller.rfunc.checker.saving;

import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractBeforeSavingCheckerTest {

    @Test
    void shouldCheckChecking() {
        String value = "value";
        ImmutableResult<String> expectedResult = ImmutableResult.<String>ok(value);
        Result<String> result = new TestChecker().apply(value);
        assertThat(expectedResult).isEqualTo(result);
    }

    private static class TestChecker extends AbstractBeforeSavingChecker<String>{}
}