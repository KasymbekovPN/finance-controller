package kpn.financecontroller.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Setter
@Configuration
@ConfigurationProperties(prefix = "i18n")
public class MessageSourceConfig {

    private String encoding;
    private String resource;

    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(resource);
        messageSource.setDefaultEncoding(encoding);

        return messageSource;
    }
}
