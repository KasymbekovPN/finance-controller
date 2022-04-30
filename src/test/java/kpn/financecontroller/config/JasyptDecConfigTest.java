package kpn.financecontroller.config;

import kpn.financecontroller.i18n.I18nService;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class JasyptDecConfigTest {

    private static final String WRONG_PASSWORD_NAME = "wrong.password.name";
    private static final String PASSWORD_NAME = "password.name";
    private static final String PASSWORD = "password";
    private static final String ERROR_MESSAGE_TEMPLATE = "error: %s";

    private static JasyptDecConfig config;

    @BeforeAll
    static void beforeAll() {
        config = new JasyptDecConfig(createEnvironment(), createI18nService());
    }

    @Test
    void shouldCheckRightPasswordChecking() {
        config.setPasswordName(PASSWORD_NAME);
        StandardPBEStringEncryptor encryptor = config.jasyptDec();
        assertThat(encryptor).isNotNull();
    }

    @Test
    void shouldCheckWrongPasswordNameChecking() {
        config.setPasswordName(WRONG_PASSWORD_NAME);
        Throwable throwable = catchThrowable(() -> {
            config.jasyptDec();
        });
        assertThat(throwable)
                .isInstanceOf(BeanCreationException.class)
                .hasMessageContaining(String.format(ERROR_MESSAGE_TEMPLATE, WRONG_PASSWORD_NAME));
    }

    private static Environment createEnvironment(){
        Environment environment = Mockito.mock(Environment.class);
        Mockito.when(environment.getProperty(WRONG_PASSWORD_NAME))
                .thenReturn(null);
        Mockito.when(environment.getProperty(PASSWORD_NAME))
                .thenReturn(PASSWORD);
        return environment;
    }

    private static I18nService createI18nService(){
        I18nService i18NService = Mockito.mock(I18nService.class);
        Mockito
                .when(i18NService.getTranslation("exception.envVar.notExist", WRONG_PASSWORD_NAME))
                .thenReturn(String.format(ERROR_MESSAGE_TEMPLATE, WRONG_PASSWORD_NAME));

        return i18NService;
    }
}