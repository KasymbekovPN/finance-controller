package kpn.financecontroller.initialization.listeners;

import kpn.financecontroller.converters.Converter;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.collector.LongKeyInitialEntityCollector;
import kpn.financecontroller.initialization.context.Context;
import kpn.financecontroller.initialization.context.ContextImpl;
import kpn.financecontroller.initialization.entities.TagInitialEntity;
import kpn.financecontroller.initialization.entityUpdater.EntityUpdater;
import kpn.financecontroller.initialization.executor.ExecutorImpl;
import kpn.financecontroller.initialization.factory.ConversionTaskFactory;
import kpn.financecontroller.initialization.factory.FileReadingTaskFactory;
import kpn.financecontroller.initialization.factory.SavingTaskFactory;
import kpn.financecontroller.initialization.task.ConversionTask;
import kpn.financecontroller.initialization.task.FileReadingTask;
import kpn.financecontroller.initialization.task.SavingTask;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@Profile("dev")
@ConfigurationProperties(prefix = "initial.entities")
public class InitialEntitiesRefreshListener implements ApplicationListener<ContextRefreshedEvent > {

    @Setter
    private boolean enable;
    @Setter
    private String directory;
    @Setter
    private List<ListItem> sequence;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        // TODO: 07.03.2022 del it
        log.info("enable: {}", enable);
        log.info("directory: {}", directory);
        log.info("sequence: {}", sequence);

        if (enable){
            log.info("Starting of initial entity saving process");

            List<String> keys = createKeys();
            Context context = createContext();
            ExecutorImpl executor = new ExecutorImpl(context);

            FileReadingTaskFactory fileReadingTaskFactory = new FileReadingTaskFactory(keys);
            executor.execute(fileReadingTaskFactory);

            ConversionTaskFactory conversionTaskFactory = new ConversionTaskFactory(keys);
            executor.execute(conversionTaskFactory);

            SavingTaskFactory savingTaskFactory = new SavingTaskFactory(keys, createCreators());
            executor.execute(savingTaskFactory);

            log.info("{}", context);
        }
    }

    private Map<String, Function<Long, SavingTask<?, ?, ?>>> createCreators() {
        Map<String, Function<Long, SavingTask<?, ?, ?>>> creatorMap = Map.of();
        return creatorMap;
    }

    private List<String> createKeys() {
        return sequence.stream().map(ListItem::getKey).collect(Collectors.toList());
    }

    private Context createContext() {
        ContextImpl context = new ContextImpl();
        for (ListItem item : sequence) {
            context.put(item.getKey(), FileReadingTask.Properties.PATH.getValue(), createPath(item.getPath()));
        }

        context.put("TAGS", ConversionTask.Properties.COLLECTOR_TYPE.getValue(), TagCollector.class);

        return context;
    }

    private String createPath(String path) {
        return directory + "/" + path;
    }

    @Setter
    @Getter
    @ToString
    public static class ListItem {
        private String key;
        private String path;
    }

    public static class TagCollector extends LongKeyInitialEntityCollector<TagInitialEntity>{}
    public static class TagCreator implements Function<String, SavingTask<TagInitialEntity, TagEntity, Tag>>{

//        private final String key;
//        private final Long entityId;
//        private final Converter<IE, E> converter;
//        private final DTOService<D, E, Long> dtoService;
//        private final EntityUpdater<IE> entityUpdater;
//        private final String collectorProperty;

        @Override
        public SavingTask<TagInitialEntity, TagEntity, Tag> apply(String s) {
            return null;
        }
    }
}
