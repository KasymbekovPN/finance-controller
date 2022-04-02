package kpn.financecontroller.initialization.listeners;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.generators.seed.*;
import kpn.financecontroller.initialization.generators.valued.Entities;
import kpn.financecontroller.initialization.generators.valued.ValuedStringGenerator;
import kpn.financecontroller.initialization.jsonObjs.TagLongKeyJsonObj;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.managers.context.ResultContextManagerImpl;
import kpn.financecontroller.initialization.storages.TagStorage;
import kpn.financecontroller.initialization.tasks.TagConversionTask;
import kpn.financecontroller.initialization.tasks.TagSavingTask;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.contexts.SimpleContext;
import kpn.taskexecutor.lib.creators.CreatorImpl;
import kpn.taskexecutor.lib.executors.SimpleExecutor;
import kpn.taskexecutor.lib.generators.Generator;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
@Profile("dev")
@ConfigurationProperties(prefix = "initial.entities")
public class InitialEntitiesRefreshListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Map<Entities, String> FILE_NAMES = Map.of(
            Entities.TAGS, "tags.json"
    );

    @Autowired
    private DTOService<Tag, TagEntity, Long> tagDtoService;

    @Setter
    private Boolean enable;
    @Setter
    private String directory;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (enable){
            log.info("DB init. data saving starting");

            CreatorImpl creator = new CreatorImpl();
            SimpleContext context = new SimpleContext();
            SimpleExecutor executor = new SimpleExecutor(creator, context);

            Generator readingGenerator = ReadingGenerator.builder()
                    .pathItem(Entities.TAGS, createPath(Entities.TAGS))
                    .managerCreator(new ManagerCreatorImpl())
                    .valuedGenerator(new ValuedStringGenerator())
                    .build();

            Generator tagCreationGenerator = CreationGenerator.builder()
                    .type(TagLongKeyJsonObj.class)
                    .managerCreator(new ManagerCreatorImpl())
                    .valuedGenerator(new ValuedStringGenerator())
                    .key(Entities.TAGS)
                    .build();

            Generator tagConversationGenerator = ConversionGenerator.builder()
                    .type(TagConversionTask.class)
                    .jsonObj(TagLongKeyJsonObj.class)
                    .managerCreator(new ManagerCreatorImpl())
                    .valuedGenerator(new ValuedStringGenerator())
                    .key(Entities.TAGS)
                    .build();

            Generator cleanupGenerator = CleanupGenerator.builder()
                    .dtoService(tagDtoService)
                    .managerCreator(new ManagerCreatorImpl())
                    .valuedGenerator(new ValuedStringGenerator())
                    .key(Entities.TAGS)
                    .build();

            Generator savingGenerator = SavingGenerator.builder()
                    .dtoService(tagDtoService)
                    .storageType(TagStorage.class)
                    .type(TagSavingTask.class)
                    .managerCreator(new ManagerCreatorImpl())
                    .valuedGenerator(new ValuedStringGenerator())
                    .key(Entities.TAGS)
                    .build();

            executor.addGenerator(readingGenerator);
            executor.addGenerator(tagCreationGenerator);
            executor.addGenerator(tagConversationGenerator);
            executor.addGenerator(cleanupGenerator);
            executor.addGenerator(savingGenerator);

            Boolean executionResult = executor.execute();
            log.info("result: {}", executionResult);
        }
    }

    private String createPath(Entities entity) {
        return directory + "/" + FILE_NAMES.getOrDefault(entity, "");
    }

    public static class ManagerCreatorImpl implements Function<Context, ResultContextManager> {
        @Override
        public ResultContextManager apply(Context context) {
            return new ResultContextManagerImpl(context, new ValuedStringGenerator());
        }
    }
}
