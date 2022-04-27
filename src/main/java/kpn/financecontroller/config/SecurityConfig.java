package kpn.financecontroller.config;

import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter;
import kpn.financecontroller.data.repos.user.UserRepo;
import kpn.financecontroller.i18n.I18nService;
import kpn.financecontroller.security.UserService;
import kpn.financecontroller.gui.views.login.LoginView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurityConfigurerAdapter {

    private final UserRepo userRepo;
    private final I18nService i18nService;

    @Autowired
    public SecurityConfig(UserRepo userRepo, I18nService i18nService) {
        this.userRepo = userRepo;
        this.i18nService = i18nService;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
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
        return new UserService(userRepo, i18nService);
    }
}
