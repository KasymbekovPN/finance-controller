package kpn.financecontroller.config;

import lombok.Setter;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(prefix = "jasypt.postgres")
public class JdbcTemplateConfig {

    private final StandardPBEStringEncryptor encryptor;

    @Setter
    private String url;
    @Setter
    private String dbName;
    @Setter
    private String username;
    @Setter
    private String password;

    @Autowired
    public JdbcTemplateConfig(StandardPBEStringEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(calculateUrl());
        dataSource.setUsername(encryptor.decrypt(username));
        dataSource.setPassword(encryptor.decrypt(password));

        return dataSource;
    }

    private String calculateUrl() {
        return url + encryptor.decrypt(dbName);
    }
}
