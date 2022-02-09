package kpn.financecontroller.config;

import kpn.financecontroller.checkers.GroupChecker;
import kpn.financecontroller.checkers.OnNullGroupChecker;
import kpn.financecontroller.initialization.collectors.LoadDataCollector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDataCollectorsCheckerConfig {

    @Bean
    public GroupChecker<LoadDataCollector<?, ?>> loadDataCollectorGroupChecker(){
        return new OnNullGroupChecker<LoadDataCollector<?, ?>>("loadDataCollectorGroupChecker");
    }
}
