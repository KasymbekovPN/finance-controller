package kpn.financecontroller.initialization.listeners;

import kpn.financecontroller.i18n.I18nService;
import kpn.financecontroller.initialization.collectors.LoadDataCollector;
import kpn.financecontroller.initialization.entities.*;
import kpn.financecontroller.initialization.load.factories.LoadingTaskFactory;
import kpn.financecontroller.initialization.load.manager.LoadingManager;
import kpn.financecontroller.initialization.load.tasks.LoadingTask;
import kpn.financecontroller.initialization.save.managers.saving.CitySaveManager;
import kpn.financecontroller.initialization.save.managers.saving.CountrySaveManager;
import kpn.financecontroller.initialization.save.managers.saving.RegionSaveManager;
import kpn.financecontroller.initialization.save.managers.saving.TagSaveManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("dev")
public class InitialEntitiesRefreshListener implements ApplicationListener<ContextRefreshedEvent > {

    @Value("${initial.entities.enable}")
    private Boolean enable;

    @Autowired
    private I18nService i18nService;
    @Autowired
    private LoadingManager loadingManager;

    @Autowired
    private LoadingTaskFactory<Long, TagInitialEntity> tagLoadingTaskFactory;
    @Autowired
    private TagSaveManager tagSaveManager;

    @Autowired
    private LoadingTaskFactory<Long, CountryInitialEntity> countryLoadingTaskFactory;
    @Autowired
    private CountrySaveManager countrySaveManager;

    @Autowired
    private LoadingTaskFactory<Long, RegionInitialEntity> regionLoadingTaskFactory;
    @Autowired
    private RegionSaveManager regionSaveManager;

    @Autowired
    private LoadingTaskFactory<Long, CityInitialEntity> cityLoadingTaskFactory;
    @Autowired
    private CitySaveManager citySaveManager;

    @Autowired
    private LoadingTaskFactory<Long, StreetInitialEntity> streetLoadingTaskFactory;
    @Autowired
    private StreetSaveManager streetSaveManager;

    @Autowired
    private LoadingTaskFactory<Long, BuildingInitialEntity> buildingLoadingTaskFactory;
    @Autowired
    private BuildingSaveManager buildingSaveManager;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (enable){
            log.info("start onApplicationEvent");

            LoadingTask<Long, TagInitialEntity> tagLoadingTask = tagLoadingTaskFactory.create();
            loadingManager.execute(tagLoadingTask);
            LoadDataCollector<Long, TagInitialEntity> tagCollector = tagLoadingTask.getCollector();

            LoadingTask<Long, CountryInitialEntity> countryLoadingTask = countryLoadingTaskFactory.create();
            loadingManager.execute(countryLoadingTask);
            LoadDataCollector<Long, CountryInitialEntity> countryCollector = countryLoadingTask.getCollector();

            LoadingTask<Long, RegionInitialEntity> regionLoadingTask = regionLoadingTaskFactory.create();
            loadingManager.execute(regionLoadingTask);
            LoadDataCollector<Long, RegionInitialEntity> regionCollector = regionLoadingTask.getCollector();

            LoadingTask<Long, CityInitialEntity> cityLoadingTask = cityLoadingTaskFactory.create();
            loadingManager.execute(cityLoadingTask);
            LoadDataCollector<Long, CityInitialEntity> cityCollector = cityLoadingTask.getCollector();

            LoadingTask<Long, StreetInitialEntity> streetLoadingTask = streetLoadingTaskFactory.create();
            loadingManager.execute(streetLoadingTask);
            LoadDataCollector<Long, StreetInitialEntity> streetCollector = streetLoadingTask.getCollector();

            LoadingTask<Long, BuildingInitialEntity> buildingLoadingTask = buildingLoadingTaskFactory.create();
            loadingManager.execute(buildingLoadingTask);
            LoadDataCollector<Long, BuildingInitialEntity> buildingCollector = buildingLoadingTask.getCollector();

            tagSaveManager.setCollector(tagCollector);
            countrySaveManager.setCollector(countryCollector);
            regionSaveManager.setRegionCollector(regionCollector);
            regionSaveManager.setCountryCollector(countryCollector);
            citySaveManager.setCityCollector(cityCollector);
            citySaveManager.setRegionCollector(regionCollector);
            streetSaveManager.setStreetCollector(streetCollector);
            streetSaveManager.setCityCollector(cityCollector);
            buildingSaveManager.setBuildingCollector(buildingCollector);
            buildingSaveManager.setStreetCollector(streetCollector);

            buildingSaveManager.clearTarget();
            streetSaveManager.clearTarget();
            citySaveManager.clearTarget();
            regionSaveManager.clearTarget();
            countrySaveManager.clearTarget();
            tagSaveManager.clearTarget();

            tagSaveManager.save();
            countrySaveManager.save();
            regionSaveManager.save();
            citySaveManager.save();
            streetSaveManager.save();
            buildingSaveManager.save();

            tagSaveManager.clearCollector();
            countrySaveManager.clearCollector();
            regionSaveManager.clearCollector();
            citySaveManager.clearCollector();
            streetSaveManager.clearCollector();
            buildingSaveManager.clearCollector();
        }
    }
}
