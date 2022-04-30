package kpn.financecontroller.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Setter
@Configuration
@ConfigurationProperties(prefix = "i18n")
public class MessageSourceConfig {

    private String encoding;
    private String resource;
    private String defaultLocale;
    private List<String> providedLocales;

    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(resource);
        messageSource.setDefaultEncoding(encoding);

        return messageSource;
    }

    @Bean
    public Locale defaultLocale(){
        return new Locale(defaultLocale);
    }

    @Bean
    public List<Locale> providedLocales(){
        return providedLocales.stream().map(Locale::new).collect(Collectors.toList());
    }
}
