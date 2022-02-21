package kpn.financecontroller.config;

import kpn.financecontroller.initialization.collectors.LoadDataCollectorImpl;
import kpn.financecontroller.initialization.entities.CityInitialEntity;
import kpn.financecontroller.initialization.entities.CountryInitialEntity;
import kpn.financecontroller.initialization.entities.RegionInitialEntity;
import kpn.financecontroller.initialization.entities.TagInitialEntity;
import kpn.financecontroller.initialization.load.creators.CollectorCreatorImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class CollectorCreatorConfig {

    @Bean
    public CollectorCreatorImpl<Long, TagInitialEntity> tagCollectorCreator(@Value("${initial.entities.ids.tags}") String id){
        return new CollectorCreatorImpl<>(id, TagLoadDataCollector.class);
    }

    private static class TagLoadDataCollector extends LoadDataCollectorImpl<Long, TagInitialEntity>{}

    @Bean
    public CollectorCreatorImpl<Long, CountryInitialEntity> countryCollectorCreator(@Value("${initial.entities.ids.countries}") String id){
        return new CollectorCreatorImpl<>(id, CountryLoadDataCollector.class);
    }

    private static class CountryLoadDataCollector extends LoadDataCollectorImpl<Long, CountryInitialEntity>{}

    @Bean
    public CollectorCreatorImpl<Long, RegionInitialEntity> regionCollectorCreator(@Value("${initial.entities.ids.regions}") String id){
        return new CollectorCreatorImpl<>(id, RegionLoadDataCollector.class);
    }

    private static class RegionLoadDataCollector extends LoadDataCollectorImpl<Long, RegionInitialEntity>{}

    @Bean
    public CollectorCreatorImpl<Long, CityInitialEntity> cityCollectorCreator(@Value("${initial.entities.ids.cities}") String id){
        return new CollectorCreatorImpl<>(id, CityLoadDataCollector.class);
    }

    private static class CityLoadDataCollector extends LoadDataCollectorImpl<Long, CityInitialEntity>{}
}
