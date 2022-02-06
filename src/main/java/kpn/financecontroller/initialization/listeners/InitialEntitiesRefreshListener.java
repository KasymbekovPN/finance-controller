package kpn.financecontroller.initialization.listeners;

import kpn.financecontroller.initialization.load.collectors.LoadDataCollector;
import kpn.financecontroller.initialization.load.entities.TagLoadEntity;
import kpn.financecontroller.initialization.load.factories.LoadingTaskFactory;
import kpn.financecontroller.initialization.load.manager.LoadingManager;
import kpn.financecontroller.initialization.load.tasks.LoadingTask;
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

//    private static final String PROPERTY = "initialEntities.directory";
    // TODO: 31.01.2022 del
//    private final IEPathsPropertyExtractor iePathsPropertyExtractor;

//    private final PropertyExtractor<String> propertyExtractor;
//    private final I18nService i18nService;
//
//    @Autowired
//    public InitialEntitiesRefreshListener(PropertyExtractor<String> propertyExtractor,
//                                          I18nService i18nService) {
//        this.propertyExtractor = propertyExtractor;
//        this.i18nService = i18nService;
//    }

    @Autowired
    private LoadingTaskFactory<Long, TagLoadEntity> tagLoadingTaskFactory;

    @Autowired
    private LoadingManager loadingManager;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("start onApplicationEvent");

        LoadingTask<Long, TagLoadEntity> tagLoadingTask = tagLoadingTaskFactory.create();
        Result<Void> result = loadingManager.execute(tagLoadingTask);
        log.info("{}", result);
        LoadDataCollector<Long, TagLoadEntity> collector = tagLoadingTask.getCollector();
        log.info("{}", collector);


//        Result<String> result = propertyExtractor.extract(PROPERTY);
//        log.info("{}", i18nService.getTranslation(result.getCode(), result.getArgs()));

        // TODO: 30.01.2022 restore
//        Result<List<String>> result = iePathsPropertyExtractor.extract();
//        log.info("{}", i18nService.getTranslation(result.getCode(), result.getArgs()));
//        if (result.getSuccess()){
//            for (String path : result.getValue()) {
//
//                System.out.println("---- " + path);
//
//                try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));){
//
//                    StringBuilder stringBuilder = new StringBuilder();
//                    reader.lines().forEach(stringBuilder::append);
//
//                    TagCollector tagCollector = new Gson().fromJson(stringBuilder.toString(), TagCollector.class);
//                    System.out.println(tagCollector);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    // TODO: 31.01.2022 del
//    @NoArgsConstructor
//    @Setter
//    @Getter
//    @ToString // TODO: 30.01.2022 del
//    private static class Collector<E>{
//        private Boolean deleteBefore;
//        private Map<Integer, E> entities;
//    }
//
//    private static class TagCollector extends Collector<TagIE>{}
}