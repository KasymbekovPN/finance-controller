package kpn.financecontroller.config;

import kpn.financecontroller.initialization.entities.CityInitialEntity;
import kpn.financecontroller.initialization.entities.CountryInitialEntity;
import kpn.financecontroller.initialization.entities.RegionInitialEntity;
import kpn.financecontroller.initialization.entities.TagInitialEntity;
import kpn.financecontroller.initialization.save.updaters.CollectorUpdaterImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class CollectorUpdaterConfig {

    @Bean
    public CollectorUpdaterImpl<Long, TagInitialEntity> tagCollectorUpdater(){
        return new CollectorUpdaterImpl<>();
    }

    @Bean
    public CollectorUpdaterImpl<Long, CountryInitialEntity> countryCollectorUpdater(){
        return new CollectorUpdaterImpl<>();
    }

    @Bean
    public CollectorUpdaterImpl<Long, RegionInitialEntity> regionCollectorUpdater(){
        return new CollectorUpdaterImpl<>();
    }

    @Bean
    public CollectorUpdaterImpl<Long, CityInitialEntity> cityCollectorUpdater(){
        return new CollectorUpdaterImpl<>();
    }
}
