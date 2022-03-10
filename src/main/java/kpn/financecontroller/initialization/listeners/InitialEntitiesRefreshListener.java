package kpn.financecontroller.initialization.listeners;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Profile("dev")
@ConfigurationProperties(prefix = "initial.entities")
public class InitialEntitiesRefreshListener implements ApplicationListener<ContextRefreshedEvent > {

    @Setter
    private List<ListItem> sequence;

    // TODO: 26.02.2022 ??? 
//    @Value("${initial.entities.enable}")
//    private Boolean enable;
//
//    @Autowired
//    private I18nService i18nService;
//    @Autowired
//    private LoadingManager loadingManager;
//
//    @Autowired
//    private LoadingTaskFactory<Long, TagInitialEntity> tagLoadingTaskFactory;
//    @Autowired
//    private TagSaveManager tagSaveManager;
//
//    @Autowired
//    private LoadingTaskFactory<Long, CountryInitialEntity> countryLoadingTaskFactory;
//    @Autowired
//    private CountrySaveManager countrySaveManager;
//
//    @Autowired
//    private LoadingTaskFactory<Long, RegionInitialEntity> regionLoadingTaskFactory;
//    @Autowired
//    private RegionSaveManager regionSaveManager;
//
//    @Autowired
//    private LoadingTaskFactory<Long, CityInitialEntity> cityLoadingTaskFactory;
//    @Autowired
//    private CitySaveManager citySaveManager;
//
//    @Autowired
//    private LoadingTaskFactory<Long, StreetInitialEntity> streetLoadingTaskFactory;
//    @Autowired
//    private StreetSaveManager streetSaveManager;
//
//    @Autowired
//    private LoadingTaskFactory<Long, BuildingInitialEntity> buildingLoadingTaskFactory;
//    @Autowired
//    private AddressSaveManager addressSaveManager;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("start onApplicationEvent");
        // TODO: 26.02.2022 ???
//        if (enable){
//            log.info("start onApplicationEvent");
//
//            LoadingTask<Long, TagInitialEntity> tagLoadingTask = tagLoadingTaskFactory.create();
//            loadingManager.execute(tagLoadingTask);
//            LoadDataCollector<Long, TagInitialEntity> tagCollector = tagLoadingTask.getCollector();
//
//            LoadingTask<Long, CountryInitialEntity> countryLoadingTask = countryLoadingTaskFactory.create();
//            loadingManager.execute(countryLoadingTask);
//            LoadDataCollector<Long, CountryInitialEntity> countryCollector = countryLoadingTask.getCollector();
//
//            LoadingTask<Long, RegionInitialEntity> regionLoadingTask = regionLoadingTaskFactory.create();
//            loadingManager.execute(regionLoadingTask);
//            LoadDataCollector<Long, RegionInitialEntity> regionCollector = regionLoadingTask.getCollector();
//
//            LoadingTask<Long, CityInitialEntity> cityLoadingTask = cityLoadingTaskFactory.create();
//            loadingManager.execute(cityLoadingTask);
//            LoadDataCollector<Long, CityInitialEntity> cityCollector = cityLoadingTask.getCollector();
//
//            LoadingTask<Long, StreetInitialEntity> streetLoadingTask = streetLoadingTaskFactory.create();
//            loadingManager.execute(streetLoadingTask);
//            LoadDataCollector<Long, StreetInitialEntity> streetCollector = streetLoadingTask.getCollector();
//
//            LoadingTask<Long, BuildingInitialEntity> buildingLoadingTask = buildingLoadingTaskFactory.create();
//            loadingManager.execute(buildingLoadingTask);
//            LoadDataCollector<Long, BuildingInitialEntity> buildingCollector = buildingLoadingTask.getCollector();
//
//            tagSaveManager.setCollector(tagCollector);
//            countrySaveManager.setCollector(countryCollector);
//            regionSaveManager.setRegionCollector(regionCollector);
//            regionSaveManager.setCountryCollector(countryCollector);
//            citySaveManager.setCityCollector(cityCollector);
//            citySaveManager.setRegionCollector(regionCollector);
//            streetSaveManager.setStreetCollector(streetCollector);
//            streetSaveManager.setCityCollector(cityCollector);
//            addressSaveManager.setBuildingCollector(buildingCollector);
//            addressSaveManager.setStreetCollector(streetCollector);
//
//            addressSaveManager.clearTarget();
//            streetSaveManager.clearTarget();
//            citySaveManager.clearTarget();
//            regionSaveManager.clearTarget();
//            countrySaveManager.clearTarget();
//            tagSaveManager.clearTarget();
//
//            tagSaveManager.save();
//            countrySaveManager.save();
//            regionSaveManager.save();
//            citySaveManager.save();
//            streetSaveManager.save();
//            addressSaveManager.save();
//
//            tagSaveManager.clearCollector();
//            countrySaveManager.clearCollector();
//            regionSaveManager.clearCollector();
//            citySaveManager.clearCollector();
//            streetSaveManager.clearCollector();
//            addressSaveManager.clearCollector();
//        }
    }

    @Setter
    @Getter
    public static class ListItem {
        private String key;
        private String path;
    }
}
