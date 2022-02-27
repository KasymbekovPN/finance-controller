package kpn.financecontroller.config;

import kpn.financecontroller.initialization.entities.*;
import kpn.financecontroller.initialization.old.entities.*;
import kpn.financecontroller.initialization.old.load.calculators.PathCalculator;
import kpn.financecontroller.initialization.old.load.creators.CollectorCreator;
import kpn.financecontroller.initialization.old.load.factories.LoadingTaskFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class LoadingTaskFactoryConfig {

    private final PathCalculator<String, String> pathCalculator;

    @Autowired
    public LoadingTaskFactoryConfig(PathCalculator<String, String> pathCalculator) {
        this.pathCalculator = pathCalculator;
    }

    @Bean
    public LoadingTaskFactoryImpl<Long, TagInitialEntity> tagLoadingTaskFactory(CollectorCreator<Long, TagInitialEntity> collectorCreator,
                                                                                @Value("${initial.entities.paths.tags}") String path,
                                                                                @Value("${initial.entities.ids.tags}") String id){
        return new LoadingTaskFactoryImpl<>(pathCalculator, collectorCreator, id, path);
    }

    @Bean
    public LoadingTaskFactoryImpl<Long, CountryInitialEntity> countryLoadingTaskFactory(CollectorCreator<Long, CountryInitialEntity> collectorCreator,
                                                                                        @Value("${initial.entities.paths.countries}") String path,
                                                                                        @Value("${initial.entities.ids.countries}") String id){
        return new LoadingTaskFactoryImpl<>(pathCalculator, collectorCreator, id, path);
    }

    @Bean
    public LoadingTaskFactoryImpl<Long, RegionInitialEntity> regionLoadingTaskFactory(CollectorCreator<Long, RegionInitialEntity> collectorCreator,
                                                                                      @Value("${initial.entities.paths.regions}") String path,
                                                                                      @Value("${initial.entities.ids.regions}") String id){
        return new LoadingTaskFactoryImpl<>(pathCalculator, collectorCreator, id, path);
    }

    @Bean
    public LoadingTaskFactoryImpl<Long, CityInitialEntity> cityLoadingTaskFactory(CollectorCreator<Long, CityInitialEntity> collectorCreator,
                                                                                  @Value("${initial.entities.paths.cities}") String path,
                                                                                  @Value("${initial.entities.ids.cities}") String id){
        return new LoadingTaskFactoryImpl<>(pathCalculator, collectorCreator, id, path);
    }

    @Bean
    public LoadingTaskFactoryImpl<Long, StreetInitialEntity> streetLoadingTaskFactory(CollectorCreator<Long, StreetInitialEntity> collectorCreator,
                                                                                      @Value("${initial.entities.paths.streets}") String path,
                                                                                      @Value("${initial.entities.ids.streets}") String id){
        return new LoadingTaskFactoryImpl<>(pathCalculator, collectorCreator, id, path);
    }

    @Bean
    public LoadingTaskFactoryImpl<Long, BuildingInitialEntity> buildingLoadingTaskFactory(CollectorCreator<Long, BuildingInitialEntity> collectorCreator,
                                                                                          @Value("${initial.entities.paths.addresses}") String path,
                                                                                          @Value("${initial.entities.ids.addresses}") String id){
        return new LoadingTaskFactoryImpl<>(pathCalculator, collectorCreator, id, path);
    }
}
