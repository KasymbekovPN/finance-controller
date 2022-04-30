package kpn.financecontroller.i18n;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class I18nServiceImplTest {

    private final I18nServiceImpl i18nService;

    @Autowired
    public I18nServiceImplTest(I18nServiceImpl i18nService) {
        this.i18nService = i18nService;
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckTranslationGetting.csv")
    void shouldCheckTranslationGetting(String code,
                                       Object arg,
                                       String localeCode,
                                       String expectedMessage) {
        String message = i18nService.getTranslation(code, new Locale(localeCode), arg);
        assertThat(expectedMessage).isEqualTo(message);
    }
}