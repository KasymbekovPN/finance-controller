package kpn.financecontroller.message;

import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class ResultMessageSeedTest {

    private static final String CODE = "code";
    private static final Object[] ARGS = {1,2,3,4,5};

    private Result<Object> result;

    @BeforeEach
    void setUp() {
        Result.Builder<Object> builder = Result.<Object>builder().code(CODE);
        Arrays.stream(ARGS).forEach(builder::arg);
        result = builder.build();
    }

    @Test
    void shouldCheckCode() {
        ResultMessageSeed seed = ResultMessageSeed.builder().result(result).build();
        assertThat(CODE).isEqualTo(seed.getCode());
    }

    @Test
    void shouldCheckLocale() {
        Locale expectedLocale = Locale.ROOT;
        ResultMessageSeed seed = ResultMessageSeed.builder().locale(expectedLocale).build();
        assertThat(expectedLocale).isEqualTo(seed.getLocale());
    }

    @Test
    void shouldCheckArgs() {
        ResultMessageSeed seed = ResultMessageSeed.builder().result(result).build();
        assertThat(ARGS).isEqualTo(seed.getArgs());
    }
}