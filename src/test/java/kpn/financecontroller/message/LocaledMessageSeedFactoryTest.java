package kpn.financecontroller.message;

import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class LocaledMessageSeedFactoryTest {

    private static final Locale LOCALE = Locale.ROOT;
    private static final String CODE = "code";
    private static final Object[] ARGS = {1,2,3,4,5};

    private final LocaledMessageSeedFactory factory;
    private final Environment environment;

    @Value("${message.seed.non-exist.code}")
    private String nonExistCode;
    @Value("${message.seed.non-exist.args}")
    private Object[] nonExistArgs;
    @Value("${message.seed.default-locale-code}")
    private String localeCode;

    private Result<Object> emptyResult;
    private Result<Object> result;
    private Locale defaultLocale;

    @Autowired
    public LocaledMessageSeedFactoryTest(LocaledMessageSeedFactory factory, Environment environment) {
        this.factory = factory;
        this.environment = environment;
    }

    @BeforeEach
    void setUp() {
        emptyResult = ImmutableResult.<Object>builder().build();

        ImmutableResult.Builder<Object> builder = ImmutableResult.<Object>builder()
                .code(CODE);
        Arrays.stream(ARGS).forEach(builder::arg);
        result = builder.build();

        defaultLocale = new Locale(localeCode);
    }

    @Test
    void shouldCheckCreationByEmptyResult() {
        LocaledMessageSeed seed = factory.create(emptyResult, LOCALE);
        assertThat(nonExistCode).isEqualTo(seed.getCode());
        assertThat(nonExistArgs).isEqualTo(seed.getArgs());
        assertThat(LOCALE).isEqualTo(seed.getLocale());
    }

    @Test
    void shouldCheckByEmptyResultWithDefaultLocale() {
        LocaledMessageSeed seed = factory.create(emptyResult);
        assertThat(nonExistCode).isEqualTo(seed.getCode());
        assertThat(nonExistArgs).isEqualTo(seed.getArgs());
        assertThat(defaultLocale).isEqualTo(seed.getLocale());
    }

    @Test
    void shouldCheckCreation() {
        LocaledMessageSeed seed = factory.create(result, LOCALE);
        assertThat(CODE).isEqualTo(seed.getCode());
        assertThat(ARGS).isEqualTo(seed.getArgs());
        assertThat(LOCALE).isEqualTo(seed.getLocale());
    }

    @Test
    void shouldCheckCreationWithDefaultLocale() {
        LocaledMessageSeed seed = factory.create(result);
        assertThat(CODE).isEqualTo(seed.getCode());
        assertThat(ARGS).isEqualTo(seed.getArgs());
        assertThat(defaultLocale).isEqualTo(seed.getLocale());
    }
}