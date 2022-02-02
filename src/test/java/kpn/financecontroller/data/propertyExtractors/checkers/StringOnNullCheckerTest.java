package kpn.financecontroller.data.propertyExtractors.checkers;

import kpn.financecontroller.result.Result;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StringOnNullCheckerTest {

    private static final String VALUE = "value";

    private static Result<String> expectedWhenNull;
    private static Result<String> expectedWhenNotNull;

    @BeforeAll
    static void beforeAll() {
        expectedWhenNull = Result.<String>builder()
                .success(false)
                .code("stringOnNullChecker.value.null")
                .build();
        expectedWhenNotNull = Result.<String>builder()
                .success(true)
                .value(VALUE)
                .code("stringOnNullChecker.value.notNull")
                .build();
    }

    @Test
    void shouldCheckStringValueOnNullCheckingWhenValueIsNull() {
        StringOnNullChecker checker = new StringOnNullChecker();
        Result<String> result = checker.check();
        assertThat(expectedWhenNull).isEqualTo(result);
    }

    @Test
    void shouldCheckStringValueOnNullCheckingWhenValueIsNotNull() {
        StringOnNullChecker checker = new StringOnNullChecker();
        checker.set(VALUE);
        Result<String> result = checker.check();
        assertThat(expectedWhenNotNull).isEqualTo(result);
    }
}