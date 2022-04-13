package kpn.financecontroller.initialization.listeners;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.generators.seed.*;
import kpn.financecontroller.initialization.generators.valued.Entities;
import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedStringGenerator;
import kpn.financecontroller.initialization.jsonObjs.CountryLongKeyJsonObj;
import kpn.financecontroller.initialization.jsonObjs.LongKeyJsonObj;
import kpn.financecontroller.initialization.jsonObjs.RegionLongKeyJsonObj;
import kpn.financecontroller.initialization.jsonObjs.TagLongKeyJsonObj;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.managers.context.ResultContextManagerImpl;
import kpn.financecontroller.initialization.setting.Setting;
import kpn.financecontroller.initialization.storages.CountryStorage;
import kpn.financecontroller.initialization.storages.RegionStorage;
import kpn.financecontroller.initialization.storages.TagStorage;
import kpn.financecontroller.initialization.tasks.conversion.CountryConversionTask;
import kpn.financecontroller.initialization.tasks.conversion.RegionConversionTask;
import kpn.financecontroller.initialization.tasks.conversion.TagConversionTask;
import kpn.financecontroller.initialization.tasks.saving.CountrySavingTask;
import kpn.financecontroller.initialization.tasks.saving.RegionSavingTask;
import kpn.financecontroller.initialization.tasks.saving.TagSavingTask;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.contexts.DefaultContext;
import kpn.taskexecutor.lib.executors.DefaultExecutor;
import kpn.taskexecutor.lib.seed.generator.Generator;
import kpn.taskexecutor.lib.task.Task;
import kpn.taskexecutor.lib.task.configurer.DefaultTaskConfigurer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Slf4j
@Component
@Profile("dev")
public class InitialEntitiesRefreshListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private DTOService<Tag, TagEntity, Long> tagDtoService;
    @Autowired
    private DTOService<Country, CountryEntity, Long> countryDtoService;
    @Autowired
    private DTOService<Region, RegionEntity, Long> regionDtoService;

    @Autowired
    private Setting setting;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (setting.isEnable()){
            log.info("DB init. data saving starting");

            DefaultTaskConfigurer taskConfigurer = DefaultTaskConfigurer.builder().build();
            DefaultContext context = new DefaultContext();
            DefaultExecutor executor = new DefaultExecutor(taskConfigurer, context);

            List<Entities> entities = List.of(
                    Entities.TAGS,
                    Entities.COUNTRIES,
                    Entities.REGIONS
            );
            Generator readingGenerator = createReadingGenerators(entities);

            List<Pair<Entities, Class<? extends LongKeyJsonObj<?>>>> pairs = List.of(
                    new Pair<>(Entities.TAGS, TagLongKeyJsonObj.class),
                    new Pair<>(Entities.COUNTRIES, CountryLongKeyJsonObj.class),
                    new Pair<>(Entities.REGIONS, RegionLongKeyJsonObj.class)
            );
            Generator creationGenerator = createCreationGenerator(pairs);

            Generator tagConversationGenerator = createConversionGenerator(Entities.TAGS, TagConversionTask.class, TagLongKeyJsonObj.class);
            Generator countryConversionGenerator = createConversionGenerator(Entities.COUNTRIES, CountryConversionTask.class, CountryLongKeyJsonObj.class);
            Generator regionConversionGenerator = createConversionGenerator(Entities.REGIONS, RegionConversionTask.class, RegionLongKeyJsonObj.class);

            List<Pair<Valued<String>, DTOService<?, ?, Long>>> cleanupInit = List.of(
                    new Pair<>(Entities.REGIONS, regionDtoService),
                    new Pair<>(Entities.COUNTRIES, countryDtoService),
                    new Pair<>(Entities.TAGS, tagDtoService)
            );
            Generator cleanupGenerator = createCleanupGenerator(cleanupInit);

            Generator tagSavingGenerator = SavingGenerator.builder()
                    .dtoService(tagDtoService)
                    .storageType(TagStorage.class)
                    .type(TagSavingTask.class)
                    .managerCreator(createManagerCreator())
                    .valuedGenerator(createValuedStringGenerator())
                    .key(Entities.TAGS)
                    .build();
            Generator countrySavingGenerator = SavingGenerator.builder()
                    .dtoService(countryDtoService)
                    .storageType(CountryStorage.class)
                    .type(CountrySavingTask.class)
                    .managerCreator(createManagerCreator())
                    .valuedGenerator(createValuedStringGenerator())
                    .key(Entities.COUNTRIES)
                    .build();
            Generator regionSavingGenerator = SavingGenerator.builder()
                    .dtoService(regionDtoService)
                    .storageType(RegionStorage.class)
                    .type(RegionSavingTask.class)
                    .managerCreator(createManagerCreator())
                    .valuedGenerator(createValuedStringGenerator())
                    .key(Entities.REGIONS)
                    .build();

            executor
                    .addGenerator(readingGenerator)
                    .addGenerator(creationGenerator)

                    .addGenerator(cleanupGenerator)

                    .addGenerator(tagConversationGenerator)
                    .addGenerator(tagSavingGenerator)

                    .addGenerator(countryConversionGenerator)
                    .addGenerator(countrySavingGenerator)

                    .addGenerator(regionConversionGenerator)
                    .addGenerator(regionSavingGenerator);






            Boolean executionResult = executor.execute();
            log.info("result: {}", executionResult);
        }
    }

    private Generator createCleanupGenerator(List<Pair<Valued<String>, DTOService<?, ?, Long>>> init) {
        CleanupGenerator.Builder builder = CleanupGenerator.builder();
        for (Pair<Valued<String>, DTOService<?, ?, Long>> item : init) {
            builder.item(item.getKey(), item.getValue());
        }
        return builder
                .managerCreator(createManagerCreator())
                .valuedGenerator(createValuedStringGenerator())
                .build();
    }

    private Generator createConversionGenerator(Valued<String> key,
                                                Class<? extends Task> taskType,
                                                Class<? extends LongKeyJsonObj<?>> jsonObjType) {
        return ConversionGenerator.builder()
                .type(taskType)
                .jsonObj(jsonObjType)
                .managerCreator(createManagerCreator())
                .valuedGenerator(createValuedStringGenerator())
                .key(key)
                .build();
    }

    private Generator createCreationGenerator(List<Pair<Entities, Class<? extends LongKeyJsonObj<?>>>> pairs) {
        CreationGenerator.Builder builder = CreationGenerator.builder();
        for (Pair<Entities, Class<? extends LongKeyJsonObj<?>>> pair : pairs) {
            builder.item(pair.getKey(), pair.getValue());
        }

        return builder
                .managerCreator(createManagerCreator())
                .valuedGenerator(createValuedStringGenerator())
                .build();
    }

    private Generator createReadingGenerators(List<Entities> entities) {
        ReadingGenerator.Builder builder = ReadingGenerator.builder();
        for (Entities entity : entities) {
            builder.pathItem(entity, setting.getPath(entity));
        }
        return builder
                .managerCreator(createManagerCreator())
                .valuedGenerator(createValuedStringGenerator())
                .build();
    }

    private ManagerCreatorImpl createManagerCreator(){
        return new ManagerCreatorImpl();
    }

    private ValuedStringGenerator createValuedStringGenerator(){
        return new ValuedStringGenerator();
    }

    public static class ManagerCreatorImpl implements Function<Context, ResultContextManager> {
        @Override
        public ResultContextManager apply(Context context) {
            return new ResultContextManagerImpl(context, new ValuedStringGenerator());
        }
    }

    @RequiredArgsConstructor
    @Getter
    private static class Pair<K, V>{
        private final K key;
        private final V value;
    }
}
