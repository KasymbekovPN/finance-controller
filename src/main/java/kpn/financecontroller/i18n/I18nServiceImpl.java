package kpn.financecontroller.i18n;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class I18nServiceImpl implements I18nService {

    private final MessageSource messageSource;
    @Getter
    private final List<Locale> providedLocales;
    private final Locale defaultLocale;

    @Autowired
    public I18nServiceImpl(MessageSource messageSource, List<Locale> providedLocales, Locale defaultLocale) {
        this.messageSource = messageSource;
        this.providedLocales = providedLocales;
        this.defaultLocale = defaultLocale;
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        try{
            return messageSource.getMessage(key, params, locale);
        } catch (NoSuchMessageException ex){
            List<Object> args = Arrays.stream(params).collect(Collectors.toList());
            return key + " " + args;
        }
    }

    @Override
    public String getTranslation(String key, Object... params) {
        return getTranslation(key, defaultLocale, params);
    }
}
