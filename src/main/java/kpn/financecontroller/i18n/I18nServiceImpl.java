package kpn.financecontroller.i18n;

import kpn.financecontroller.message.LocaledMessageSeed;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@Setter
@ConfigurationProperties(prefix = "i18n")
public class I18nServiceImpl implements I18nService {

    private final MessageSource messageSource;

    private List<Locale> providedLocales;
    private Locale defaultLocale;

    @Autowired
    public I18nServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getTranslation(LocaledMessageSeed seed) {
        return getTranslation(seed.getCode(), seed.getLocale(), seed.getArgs());
    }

    @Override
    public String getTranslation(String key, Object... params) {
        return getTranslation(key, defaultLocale, params);
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        try{
            return messageSource.getMessage(key, params, locale);
        } catch (NoSuchMessageException ex){
            return key + " " + List.of(params);
        }
    }

    @Override
    public List<Locale> getProvidedLocales() {
        return providedLocales;
    }
}
