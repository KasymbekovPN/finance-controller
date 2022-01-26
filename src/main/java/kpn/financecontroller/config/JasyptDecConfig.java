package kpn.financecontroller.config;

import kpn.financecontroller.i18n.I18nService;
import lombok.Setter;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Setter
@Configuration
@ConfigurationProperties(prefix = "jasypt")
public class JasyptDecConfig {

    private final Environment environment;
    private final I18nService i18nService;

    private String passwordName;

    @Autowired
    public JasyptDecConfig(Environment environment, I18nService i18nService) {
        this.environment = environment;
        this.i18nService = i18nService;
    }

    @Bean
    public StandardPBEStringEncryptor jasyptDec(){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        String password = getPassword();
        encryptor.setPassword(password);
        return encryptor;
    }

    private String getPassword() {
        String password = environment.getProperty(passwordName);
        checkGottenPassword(password);
        return password;
    }

    private void checkGottenPassword(String password) {
        if (password == null){
            String message = i18nService.getTranslation("exception.envVar.notExist", passwordName);
            throw new BeanCreationException(message);
        }
    }
}
