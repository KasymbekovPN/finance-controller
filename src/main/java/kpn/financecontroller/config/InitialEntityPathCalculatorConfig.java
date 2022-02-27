package kpn.financecontroller.config;

import kpn.financecontroller.initialization.old.load.calculators.PathCalculator;
import kpn.financecontroller.initialization.old.load.calculators.SimplePathCalculator;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

// TODO: 27.02.2022 del ????
@Setter
@Configuration
@ConfigurationProperties(prefix = "initial.entities")
@Profile("dev")
public class InitialEntityPathCalculatorConfig {

    private String directory;

    @Bean
    public PathCalculator<String, String> initialEntityPathCalculator(){
        return new SimplePathCalculator(directory);
    }
}
