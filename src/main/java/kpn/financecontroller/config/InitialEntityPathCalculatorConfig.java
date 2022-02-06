package kpn.financecontroller.config;

import kpn.financecontroller.data.propertyExtractors.calculators.PathCalculator;
import kpn.financecontroller.data.propertyExtractors.calculators.SimplePathCalculator;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

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
