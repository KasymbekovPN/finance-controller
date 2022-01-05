package kpn.financecontroller.config;

import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter;
import kpn.financecontroller.data.repo.user.UserRepo;
import kpn.financecontroller.data.service.user.UserService;
import kpn.financecontroller.gui.views.login.LoginView;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurityConfigurerAdapter {

    private final UserRepo userRepo;
    private final StandardPBEStringEncryptor encryptor;

    @Autowired
    public SecurityConfig(UserRepo userRepo, StandardPBEStringEncryptor encryptor) {
        this.userRepo = userRepo;
        this.encryptor = encryptor;
    }

    // TODO: 04.01.2022 use BCryptPasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().antMatchers("/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        setLoginView(http, LoginView.class, "/logout");
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new UserService(userRepo, encryptor);
    }
}
