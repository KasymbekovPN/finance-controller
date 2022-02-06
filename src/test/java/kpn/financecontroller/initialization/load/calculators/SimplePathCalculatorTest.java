package kpn.financecontroller.initialization.load.calculators;

import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimplePathCalculatorTest {

    private static final String FULL_PATH = "basePath/addPath.txt";
    private static final String BASE_PATH = "basePath";
    private static final String ADD_PATH = "addPath.txt";

    private static Result<String> expectedResult;
    private static Result<String> expectedResultWhenBaseNull;
    private static Result<String> expectedResultWhenAddNull;

    @BeforeAll
    static void beforeAll() {
        expectedResultWhenBaseNull = Result.<String>builder()
                .success(false)
                .code("simplePathCalculator.base.null")
                .build();
        expectedResultWhenAddNull = Result.<String>builder()
                .success(false)
                .code("simplePathCalculator.add.null")
                .build();
        expectedResult = Result.<String>builder()
                .success(true)
                .value(FULL_PATH)
                .build();
    }

    @Test
    void shouldCheckWhenBaseIsNull() {
        SimplePathCalculator calculator = new SimplePathCalculator(null);
        Result<String> result = calculator.calculate(ADD_PATH);
        assertThat(expectedResultWhenBaseNull).isEqualTo(result);
    }

    @Test
    void shouldCheckWhenAddIsNull() {
        SimplePathCalculator calculator = new SimplePathCalculator(BASE_PATH);
        Result<String> result = calculator.calculate(null);
        assertThat(expectedResultWhenAddNull).isEqualTo(result);
    }

    @Test
    void shouldCheckPathCalculation() {
        SimplePathCalculator calculator = new SimplePathCalculator(BASE_PATH);
        Result<String> result = calculator.calculate(ADD_PATH);
        assertThat(expectedResult).isEqualTo(result);
    }
}