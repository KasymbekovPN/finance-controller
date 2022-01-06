package kpn.financecontroller.result;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ResultTest {

    @Test
    void shouldCheckSuccess() {
        boolean expectedSuccess = true;
        Result<String> result = Result.<String>builder().success(expectedSuccess).build();
        assertThat(expectedSuccess).isEqualTo(result.getSuccess());
    }

    @Test
    void shouldCheckValue() {
        String expectedValue = "value";
        Result<String> result = Result.<String>builder().value(expectedValue).build();
        assertThat(expectedValue).isEqualTo(result.getValue());
    }

    @Test
    void shouldCheckCode() {
        String expectedCode = "code";
        Result<String> result = Result.<String>builder().code(expectedCode).build();
        assertThat(expectedCode).isEqualTo(result.getCode());
    }

    @Test
    void shouldCheckArgs() {
        Result.Builder<String> builder = Result.<String>builder();
        List<Integer> list = List.of(1, 2, 3);
        list.forEach(builder::arg);
        Result<String> result = builder.build();
        assertThat(list.toArray()).isEqualTo(result.getArgs());
    }
}
