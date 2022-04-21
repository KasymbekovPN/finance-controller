package kpn.financecontroller.initialization.listeners;

import com.google.gson.Gson;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.entities.*;
import kpn.financecontroller.initialization.generators.seed.*;
import kpn.financecontroller.initialization.generators.valued.*;
import kpn.financecontroller.initialization.listeners.jobjects.*;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.managers.context.ResultContextManagerImpl;
import kpn.financecontroller.initialization.setting.Setting;
import kpn.financecontroller.initialization.storage.ObjectStorage;
import kpn.financecontroller.initialization.tasks.CreationTask;
import kpn.financecontroller.initialization.tasks.ConversionTask;
import kpn.financecontroller.initialization.tasks.SavingTask;
import kpn.lib.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.contexts.DefaultContext;
import kpn.taskexecutor.lib.executors.DefaultExecutor;
import kpn.taskexecutor.lib.seed.generator.Generator;
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
import java.util.Optional;
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
    private DTOService<City, CityEntity, Long> cityDtoService;
    @Autowired
    private DTOService<Street, StreetEntity, Long> streetDtoService;

    @Autowired
    private Setting setting;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (setting.isEnable()){
            log.info("DB init. data saving starting");

            // TODO: 21.04.2022 restore
//            DefaultTaskConfigurer taskConfigurer = DefaultTaskConfigurer.builder().build();
//            DefaultContext context = new DefaultContext();
//            DefaultExecutor executor = new DefaultExecutor(taskConfigurer, context);
//
//            List<Entities> entities = List.of(
//                    Entities.TAGS,
//                    Entities.COUNTRIES,
//                    Entities.REGIONS,
//                    Entities.CITIES,
//                    Entities.STREETS
//            );
//            Generator readingGenerator = createReadingGenerators(entities);
//
//            CreationTask.ObjectStorageCreatorOld tagOSC = (str) -> {
//                TagLongKeyJsonObj jsonObj = new Gson().fromJson(str, TagLongKeyJsonObj.class);
//                ObjectStorage storage = new ObjectStorage();
//                storage.putAll(jsonObj.getEntities());
//                return storage;
//            };
//            CreationTask.ObjectStorageCreatorOld countryOSC = (str) -> {
//                CountryLongKeyJsonObj jsonObj = new Gson().fromJson(str, CountryLongKeyJsonObj.class);
//                ObjectStorage storage = new ObjectStorage();
//                storage.putAll(jsonObj.getEntities());
//                return storage;
//            };
//            CreationTask.ObjectStorageCreatorOld regionOSC = (str) -> {
//                RegionLongKeyJsonObj jsonObj = new Gson().fromJson(str, RegionLongKeyJsonObj.class);
//                ObjectStorage storage = new ObjectStorage();
//                storage.putAll(jsonObj.getEntities());
//                return storage;
//            };
//            CreationTask.ObjectStorageCreatorOld cityOSC = (str) -> {
//                CityLongKeyJsonObj jsonObj = new Gson().fromJson(str, CityLongKeyJsonObj.class);
//                ObjectStorage storage = new ObjectStorage();
//                storage.putAll(jsonObj.getEntities());
//                return storage;
//            };
//            CreationTask.ObjectStorageCreatorOld streetOSC = (str) -> {
//                StreetLongKeyJsonObj jsonObj = new Gson().fromJson(str, StreetLongKeyJsonObj.class);
//                ObjectStorage storage = new ObjectStorage();
//                storage.putAll(jsonObj.getEntities());
//                return storage;
//            };
//            List<Pair<Entities, CreationTask.ObjectStorageCreatorOld>> pairs = List.of(
//                    new Pair<>(Entities.TAGS, tagOSC),
//                    new Pair<>(Entities.COUNTRIES, countryOSC),
//                    new Pair<>(Entities.REGIONS, regionOSC),
//                    new Pair<>(Entities.CITIES, cityOSC),
//                    new Pair<>(Entities.STREETS, streetOSC)
//            );
//            Generator creationGenerator = createCreationGenerator(pairs);
//
//            ConversionTask.Strategy tagFillingStrategy = (storage, value, manager) -> {
//                TagJsonEntity jsonEntity = (TagJsonEntity) value;
//                TagEntity entity = new TagEntity();
//                entity.setId(jsonEntity.getId());
//                entity.setName(jsonEntity.getName());
//                storage.put(jsonEntity.getId(), entity);
//                return Optional.empty();
//            };
//            ConversionTask.Strategy countryFillingStrategy = (storage, value, manager) -> {
//                CountryJsonEntity jsonEntity = (CountryJsonEntity) value;
//                CountryEntity entity = new CountryEntity();
//                entity.setId(jsonEntity.getId());
//                entity.setName(jsonEntity.getName());
//                storage.put(jsonEntity.getId(), entity);
//
//                return Optional.empty();
//            };
//            ConversionTask.Strategy regionFillingStrategy = (storage, value, manager) -> {
//                RegionJsonEntity jsonEntity = (RegionJsonEntity) value;
//
//                Result<ObjectStorage> result = manager.get(Entities.COUNTRIES, Properties.JSON_TO_DB_CONVERSION_RESULT, ObjectStorage.class);
//                if (result.isSuccess() && result.getValue().containsKey(jsonEntity.getCountryId())){
//                    RegionEntity entity = new RegionEntity();
//                    entity.setId(jsonEntity.getId());
//                    entity.setName(jsonEntity.getName());
//                    entity.setCountryEntity((CountryEntity) result.getValue().get(jsonEntity.getCountryId()));
//
//                    storage.put(jsonEntity.getId(), entity);
//
//                    return Optional.empty();
//                }
//
//                return Optional.of(Codes.ENTITY_CONVERSION_FAIL);
//            };
//            ConversionTask.Strategy cityFillingStrategy = (storage, value, manager) -> {
//                CityJsonEntity jsonEntity = (CityJsonEntity) value;
//
//                Result<ObjectStorage> result = manager.get(Entities.REGIONS, Properties.JSON_TO_DB_CONVERSION_RESULT, ObjectStorage.class);
//                if (result.isSuccess() && result.getValue().containsKey(jsonEntity.getRegionId())){
//                    CityEntity entity = new CityEntity();
//                    entity.setId(jsonEntity.getId());
//                    entity.setName(jsonEntity.getName());
//                    entity.setRegionEntity((RegionEntity) result.getValue().get(jsonEntity.getRegionId()));
//
//                    storage.put(jsonEntity.getId(), entity);
//
//                    return Optional.empty();
//                }
//
//                return Optional.of(Codes.ENTITY_CONVERSION_FAIL);
//            };
//            ConversionTask.Strategy streetFillingStrategy = (storage, value, manager) -> {
//                StreetJsonEntity jsonEntity = (StreetJsonEntity) value;
//
//                Result<ObjectStorage> result = manager.get(Entities.CITIES, Properties.JSON_TO_DB_CONVERSION_RESULT, ObjectStorage.class);
//                if (result.isSuccess() && result.getValue().containsKey(jsonEntity.getCityId())){
//                    StreetEntity entity = new StreetEntity();
//                    entity.setId(jsonEntity.getId());
//                    entity.setName(jsonEntity.getName());
//                    entity.setCityEntity((CityEntity) result.getValue().get(jsonEntity.getCityId()));
//
//                    storage.put(jsonEntity.getId(), entity);
//
//                    return Optional.empty();
//                }
//
//                return Optional.of(Codes.ENTITY_CONVERSION_FAIL);
//            };
//            Generator tagConversationGenerator = createConversionGenerator(Entities.TAGS, tagFillingStrategy);
//            Generator countryConversionGenerator = createConversionGenerator(Entities.COUNTRIES, countryFillingStrategy);
//            Generator regionConversionGenerator = createConversionGenerator(Entities.REGIONS, regionFillingStrategy);
//            Generator cityConversionGenerator = createConversionGenerator(Entities.CITIES, cityFillingStrategy);
//            Generator streetConversionGenerator = createConversionGenerator(Entities.STREETS, streetFillingStrategy);
//
//            List<Pair<Valued<String>, DTOService<?, ?, Long>>> cleanupInit = List.of(
//                    new Pair<>(Entities.STREETS, streetDtoService),
//                    new Pair<>(Entities.CITIES, cityDtoService),
//                    new Pair<>(Entities.REGIONS, regionDtoService),
//                    new Pair<>(Entities.COUNTRIES, countryDtoService),
//                    new Pair<>(Entities.TAGS, tagDtoService)
//            );
//            Generator cleanupGenerator = createCleanupGenerator(cleanupInit);
//
//            //<
//            SavingTask.Strategy tagSavingStrategy = value -> {
//                TagEntity entity = (TagEntity) value;
//                Result<Tag> result = tagDtoService.saver().save(entity);
//                if (result.isSuccess()){
//                    entity.setId(result.getValue().getId());
//                    return Optional.empty();
//                }
//                return Optional.of(Codes.FAIL_SAVING_ATTEMPT);
//            };
//            SavingTask.Strategy countrySavingStrategy = value -> {
//                CountryEntity entity = (CountryEntity) value;
//                Result<Country> result = countryDtoService.saver().save(entity);
//                if (result.isSuccess()){
//                    entity.setId(result.getValue().getId());
//                    return Optional.empty();
//                }
//                return Optional.of(Codes.FAIL_SAVING_ATTEMPT);
//            };
//            SavingTask.Strategy regionSavingStrategy =value -> {
//                RegionEntity entity = (RegionEntity) value;
//                Result<Region> result = regionDtoService.saver().save(entity);
//                if (result.isSuccess()){
//                    entity.setId(result.getValue().getId());
//                    return Optional.empty();
//                }
//                return Optional.of(Codes.FAIL_SAVING_ATTEMPT);
//            };
//            SavingTask.Strategy citySavingStrategy = value -> {
//                CityEntity entity = (CityEntity) value;
//                Result<City> result = cityDtoService.saver().save(entity);
//                if (result.isSuccess()){
//                    entity.setId(result.getValue().getId());
//                    return Optional.empty();
//                }
//                return Optional.of(Codes.FAIL_SAVING_ATTEMPT);
//            };
//            SavingTask.Strategy streetSavingStrategy = value -> {
//                StreetEntity entity = (StreetEntity) value;
//                Result<Street> result = streetDtoService.saver().save(entity);
//                if (result.isSuccess()){
//                    entity.setId(result.getValue().getId());
//                    return Optional.empty();
//                }
//                return Optional.of(Codes.FAIL_SAVING_ATTEMPT);
//            };
//            Generator tagSavingGenerator = createSavingGenerator(Entities.TAGS, tagSavingStrategy);
//            Generator countrySavingGenerator = createSavingGenerator(Entities.COUNTRIES, countrySavingStrategy);
//            Generator regionSavingGenerator = createSavingGenerator(Entities.REGIONS, regionSavingStrategy);
//            Generator citySavingGenerator = createSavingGenerator(Entities.CITIES, citySavingStrategy);
//            Generator streetSavingGenerator = createSavingGenerator(Entities.STREETS, streetSavingStrategy);
//
//            executor
//                    .addGenerator(readingGenerator)
//                    .addGenerator(creationGenerator)
//                    .addGenerator(cleanupGenerator)
//
//                    .addGenerator(tagConversationGenerator)
//                    .addGenerator(tagSavingGenerator)
//
//                    .addGenerator(countryConversionGenerator)
//                    .addGenerator(countrySavingGenerator)
//
//                    .addGenerator(regionConversionGenerator)
//                    .addGenerator(regionSavingGenerator)
//
//                    .addGenerator(cityConversionGenerator)
//                    .addGenerator(citySavingGenerator)
//
//                    .addGenerator(streetConversionGenerator)
//                    .addGenerator(streetSavingGenerator);
//
//            Boolean executionResult = executor.execute();
//            log.info("result: {}", executionResult);
        }
    }

    // TODO: 21.04.2022 restore
//    private Generator createCleanupGenerator(List<Pair<Valued<String>, DTOService<?, ?, Long>>> init) {
//        CleanupGenerator.Builder builder = CleanupGenerator.builder();
//        for (Pair<Valued<String>, DTOService<?, ?, Long>> item : init) {
//            builder.item(item.getKey(), item.getValue());
//        }
//        return builder
//                .managerCreator(createManagerCreator())
//                .valuedGenerator(createValuedStringGenerator())
//                .build();
//    }
//
//
//    private Generator createConversionGenerator(Valued<String> key,
//                                                ConversionTask.Strategy strategy) {
//        return ConversionGenerator.builder()
//                .type(ConversionTask.class)
//                .objectStorageFiller(strategy)
//                .managerCreator(createManagerCreator())
//                .valuedGenerator(createValuedStringGenerator())
//                .key(key)
//                .build();
//    }
//
//    private Generator createCreationGenerator(List<Pair<Entities, CreationTask.ObjectStorageCreatorOld>> pairs) {
//        CreationGenerator.Builder builder = CreationGenerator.builder();
//        for (Pair<Entities, CreationTask.ObjectStorageCreatorOld> pair : pairs) {
//            builder.item(pair.getKey(), pair.getValue());
//        }
//
//        return builder
//                .managerCreator(createManagerCreator())
//                .valuedGenerator(createValuedStringGenerator())
//                .build();
//    }
//
//    private Generator createReadingGenerators(List<Entities> entities) {
//        ReadingGenerator.Builder builder = ReadingGenerator.builder();
//        for (Entities entity : entities) {
//            builder.pathItem(entity, setting.getPath(entity));
//        }
//        return builder
//                .managerCreator(createManagerCreator())
//                .valuedGenerator(createValuedStringGenerator())
//                .build();
//    }
//
//    private Generator createSavingGenerator(Valued<String> key, SavingTask.Strategy strategy) {
//        return SavingGenerator.builder()
//                .strategy(strategy)
//                .managerCreator(createManagerCreator())
//                .valuedGenerator(createValuedStringGenerator())
//                .key(key)
//                .build();
//    }

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
