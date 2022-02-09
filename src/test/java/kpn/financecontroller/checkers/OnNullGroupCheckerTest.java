package kpn.financecontroller.checkers;

import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OnNullGroupCheckerTest {

    private static final String ID = "id";
    private static final String NULL_KEY_0 = "null.key.0";
    private static final String NULL_KEY_1 = "null.key.1";
    private static final String NOT_NULL_KEY_0 = "not.null.key.0";
    private static final String NOT_NULL_KEY_1 = "not.null.key.1";

    private static Result<Void> expectedResultWhenCollectionIsEmpty;
    private static Result<Void> expectedResultWhenItemNull;
    private static Result<Void> expectedResult;

    @BeforeAll
    static void beforeAll() {
        expectedResultWhenCollectionIsEmpty = Result.<Void>builder()
                .success(true)
                .code("groupChecker.collection.empty")
                .arg(ID)
                .build();
        expectedResultWhenItemNull = Result.<Void>builder()
                .success(false)
                .code("groupChecker.items.null")
                .arg(NULL_KEY_0 + ", " + NULL_KEY_1)
                .arg(ID)
                .build();
        expectedResult = Result.<Void>builder()
                .success(true)
                .code("groupChecker.checking.success")
                .arg(ID)
                .build();
    }

    @Test
    void shouldCheckEmptyCollection() {
        OnNullGroupChecker<String> checker = new OnNullGroupChecker<>(ID);
        Result<Void> result = checker.check();
        assertThat(expectedResultWhenCollectionIsEmpty).isEqualTo(result);
    }

    @Test
    void shouldCheckIfCollectionItemIsNull() {
        OnNullGroupChecker<String> checker = new OnNullGroupChecker<>(ID);
        Result<Void> result = checker
                .set(NULL_KEY_0, null)
                .set(NULL_KEY_1, null)
                .set(NOT_NULL_KEY_0, "")
                .check();
        assertThat(expectedResultWhenItemNull.getSuccess()).isEqualTo(result.getSuccess());
        assertThat(expectedResultWhenItemNull.getCode()).isEqualTo(result.getCode());
    }

    @Test
    void shouldCheckChecking() {
        OnNullGroupChecker<String> checker = new OnNullGroupChecker<>(ID);
        Result<Void> result = checker
                .set(NOT_NULL_KEY_0, "")
                .set(NOT_NULL_KEY_1, "")
                .check();
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheckReset() {
        OnNullGroupChecker<String> checker = new OnNullGroupChecker<>(ID);
        Result<Void> result = checker
                .set(NOT_NULL_KEY_0, "")
                .reset()
                .check();
        assertThat(expectedResultWhenCollectionIsEmpty).isEqualTo(result);
    }
}