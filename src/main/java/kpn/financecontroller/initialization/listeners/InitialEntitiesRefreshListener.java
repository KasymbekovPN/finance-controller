package kpn.financecontroller.initialization.listeners;

import kpn.financecontroller.i18n.I18nService;
import kpn.financecontroller.initialization.collectors.LoadDataCollector;
import kpn.financecontroller.initialization.entities.CountryInitialEntity;
import kpn.financecontroller.initialization.entities.TagInitialEntity;
import kpn.financecontroller.initialization.load.factories.LoadingTaskFactory;
import kpn.financecontroller.initialization.load.manager.LoadingManager;
import kpn.financecontroller.initialization.load.tasks.LoadingTask;
import kpn.financecontroller.initialization.save.managers.CountrySaveManager;
import kpn.financecontroller.initialization.save.managers.SaveManager;
import kpn.financecontroller.initialization.save.managers.TagSaveManager;
import kpn.financecontroller.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("dev")
public class InitialEntitiesRefreshListener implements ApplicationListener<ContextRefreshedEvent > {

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

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("start onApplicationEvent");

        LoadingTask<Long, TagInitialEntity> tagLoadingTask = tagLoadingTaskFactory.create();
        loadingManager.execute(tagLoadingTask);
        LoadDataCollector<Long, TagInitialEntity> tagCollector = tagLoadingTask.getCollector();

        LoadingTask<Long, CountryInitialEntity> countryLoadingTask = countryLoadingTaskFactory.create();
        loadingManager.execute(countryLoadingTask);
        LoadDataCollector<Long, CountryInitialEntity> countryCollector = countryLoadingTask.getCollector();

        tagSaveManager.setCollector(tagCollector);
        countrySaveManager.setCollector(countryCollector);

        tagSaveManager.clearTarget();
        countrySaveManager.clearTarget();

        tagSaveManager.save();
        countrySaveManager.save();

        tagSaveManager.clearCollector();
        countrySaveManager.clearCollector();
    }
}
