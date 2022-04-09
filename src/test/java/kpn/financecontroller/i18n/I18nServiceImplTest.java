package kpn.financecontroller.i18n;

import kpn.financecontroller.message.LocaledMessageSeed;
import kpn.financecontroller.message.LocaledMessageSeedFactory;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class I18nServiceImplTest {

    private final I18nServiceImpl service;
    private final LocaledMessageSeedFactory seedFactory;

    @Autowired
    public I18nServiceImplTest(I18nServiceImpl service, LocaledMessageSeedFactory seedFactory) {
        this.service = service;
        this.seedFactory = seedFactory;
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckTranslationGetting.csv")
    void shouldCheckTranslationGetting(String code,
                                       Object arg,
                                       String localeCode,
                                       String expectedResult) {
        String result = service.getTranslation(code, new Locale(localeCode), arg);
        assertThat(expectedResult).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckTranslationGetting.csv")
    void shouldCheckTranslationGettingBySeed(String code,
                                       Object arg,
                                       String localeCode,
                                       String expectedResult) {
        Result<Object> result = ImmutableResult.<Object>builder().code(code).arg(arg).build();
        LocaledMessageSeed seed = seedFactory.create(result, new Locale(localeCode));
        String translation = service.getTranslation(seed);
        assertThat(expectedResult).isEqualTo(translation);
    }

    @Test
    void shouldCheckProvidedLocales() {
        List<Locale> locales = List.of(new Locale("en"), new Locale("ru"));
        assertThat(locales).isEqualTo(service.getProvidedLocales());
    }
}