package kpn.financecontroller.initialization.listeners;

import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.address.AddressEntity;
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
    private DTOService<Address, AddressEntity, Long> addressDtoService;

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

            Generator readingGenerator = createReadingGenerators(List.of(
                            Entities.TAGS,
                            Entities.COUNTRIES,
                            Entities.REGIONS,
                            Entities.CITIES,
                            Entities.STREETS,
                            Entities.ADDRESSES
            ));
            executor.addGenerator(readingGenerator);

            Generator creationGenerator = createCreationGenerator(List.of(
                    new Pair<>(Entities.TAGS, new CreationTask.ObjectStorageCreator(TagLongKeyJsonObj.class)),
                    new Pair<>(Entities.COUNTRIES, new CreationTask.ObjectStorageCreator(CountryLongKeyJsonObj.class)),
                    new Pair<>(Entities.REGIONS, new CreationTask.ObjectStorageCreator(RegionLongKeyJsonObj.class)),
                    new Pair<>(Entities.CITIES, new CreationTask.ObjectStorageCreator(CityLongKeyJsonObj.class)),
                    new Pair<>(Entities.STREETS, new CreationTask.ObjectStorageCreator(StreetLongKeyJsonObj.class)),
                    new Pair<>(Entities.ADDRESSES, new CreationTask.ObjectStorageCreator(AddressLongKeyJsonObj.class))
            ));
            executor.addGenerator(creationGenerator);

            Generator cleanupGenerator = createCleanupGenerator(List.of(
                    new Pair<>(Entities.ADDRESSES, addressDtoService),
                    new Pair<>(Entities.STREETS, streetDtoService),
                    new Pair<>(Entities.CITIES, cityDtoService),
                    new Pair<>(Entities.REGIONS, regionDtoService),
                    new Pair<>(Entities.COUNTRIES, countryDtoService),
                    new Pair<>(Entities.TAGS, tagDtoService)
            ));
            executor.addGenerator(cleanupGenerator);

            executor
                    .addGenerator(createConversionGenerator(Entities.TAGS, createTagFillingStrategy()))
                    .addGenerator(createSavingGenerator(Entities.TAGS, createTagSavingStrategy()))

                    .addGenerator(createConversionGenerator(Entities.COUNTRIES, createCountryFillingStrategy()))
                    .addGenerator(createSavingGenerator(Entities.COUNTRIES, createCountrySavingStrategy()))

                    .addGenerator(createConversionGenerator(Entities.REGIONS, createRegionFillingStrategy()))
                    .addGenerator(createSavingGenerator(Entities.REGIONS, createRegionSavingStrategy()))

                    .addGenerator(createConversionGenerator(Entities.CITIES, createCityFillingStrategy()))
                    .addGenerator(createSavingGenerator(Entities.CITIES, createCitySavingStrategy()))

                    .addGenerator(createConversionGenerator(Entities.STREETS, createStreetFillingStrategy()))
                    .addGenerator(createSavingGenerator(Entities.STREETS, createStreetSavingStrategy()))

                    .addGenerator(createConversionGenerator(Entities.ADDRESSES, createAddressFillingStrategy()))
                    .addGenerator(createSavingGenerator(Entities.ADDRESSES, createAddressSavingStrategy()));

            Boolean executionResult = executor.execute();
            log.info("result: {}", executionResult);
        }
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

    private Generator createCreationGenerator(List<Pair<Entities, CreationTask.ObjectStorageCreator>> pairs) {
        CreationGenerator.Builder builder = CreationGenerator.builder();
        for (Pair<Entities, CreationTask.ObjectStorageCreator> pair : pairs) {
            builder.item(pair.getKey(), pair.getValue());
        }

        return builder
                .managerCreator(createManagerCreator())
                .valuedGenerator(createValuedStringGenerator())
                .build();
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
                                                ConversionTask.Strategy strategy) {
        return ConversionGenerator.builder()
                .type(ConversionTask.class)
                .objectStorageFiller(strategy)
                .managerCreator(createManagerCreator())
                .valuedGenerator(createValuedStringGenerator())
                .key(key)
                .build();
    }

    private Generator createSavingGenerator(Valued<String> key, SavingTask.Strategy strategy) {
        return SavingGenerator.builder()
                .strategy(strategy)
                .managerCreator(createManagerCreator())
                .valuedGenerator(createValuedStringGenerator())
                .key(key)
                .build();
    }

    private SavingTask.Strategy createTagSavingStrategy() {
        return value -> {
            TagEntity entity = (TagEntity) value;
            Result<Tag> result = tagDtoService.saver().save(entity);
            if (result.isSuccess()){
                entity.setId(result.getValue().getId());
                return Optional.empty();
            }
            return Optional.of(Codes.FAIL_SAVING_ATTEMPT);
        };
    }

    private SavingTask.Strategy createCountrySavingStrategy() {
        return value -> {
            CountryEntity entity = (CountryEntity) value;
            Result<Country> result = countryDtoService.saver().save(entity);
            if (result.isSuccess()){
                entity.setId(result.getValue().getId());
                return Optional.empty();
            }
            return Optional.of(Codes.FAIL_SAVING_ATTEMPT);
        };
    }

    private SavingTask.Strategy createRegionSavingStrategy() {
        return value -> {
            RegionEntity entity = (RegionEntity) value;
            Result<Region> result = regionDtoService.saver().save(entity);
            if (result.isSuccess()){
                entity.setId(result.getValue().getId());
                return Optional.empty();
            }
            return Optional.of(Codes.FAIL_SAVING_ATTEMPT);
        };
    }

    private SavingTask.Strategy createCitySavingStrategy() {
        return value -> {
            CityEntity entity = (CityEntity) value;
            Result<City> result = cityDtoService.saver().save(entity);
            if (result.isSuccess()){
                entity.setId(result.getValue().getId());
                return Optional.empty();
            }
            return Optional.of(Codes.FAIL_SAVING_ATTEMPT);
        };
    }

    private SavingTask.Strategy createStreetSavingStrategy() {
        return value -> {
            StreetEntity entity = (StreetEntity) value;
            Result<Street> result = streetDtoService.saver().save(entity);
            if (result.isSuccess()){
                entity.setId(result.getValue().getId());
                return Optional.empty();
            }
            return Optional.of(Codes.FAIL_SAVING_ATTEMPT);
        };
    }

    private SavingTask.Strategy createAddressSavingStrategy() {
        return value -> {
            AddressEntity entity = (AddressEntity) value;
            Result<Address> result = addressDtoService.saver().save(entity);
            if (result.isSuccess()){
                entity.setId(result.getValue().getId());
                return Optional.empty();
            }
            return Optional.of(Codes.FAIL_SAVING_ATTEMPT);
        };
    }

    private ConversionTask.Strategy createTagFillingStrategy() {
        return (storage, value, manager) -> {
            TagJsonEntity jsonEntity = (TagJsonEntity) value;
            TagEntity entity = new TagEntity();
            entity.setId(jsonEntity.getId());
            entity.setName(jsonEntity.getName());
            storage.put(jsonEntity.getId(), entity);
            return Optional.empty();
        };
    }

    private ConversionTask.Strategy createCountryFillingStrategy() {
        return (storage, value, manager) -> {
            CountryJsonEntity jsonEntity = (CountryJsonEntity) value;
            CountryEntity entity = new CountryEntity();
            entity.setId(jsonEntity.getId());
            entity.setName(jsonEntity.getName());
            storage.put(jsonEntity.getId(), entity);

            return Optional.empty();
        };
    }

    private ConversionTask.Strategy createRegionFillingStrategy() {
        return  (storage, value, manager) -> {
            RegionJsonEntity jsonEntity = (RegionJsonEntity) value;

            Result<ObjectStorage> result = manager.get(Entities.COUNTRIES, Properties.JSON_TO_DB_CONVERSION_RESULT, ObjectStorage.class);
            if (result.isSuccess() && result.getValue().containsKey(jsonEntity.getCountryId())){
                RegionEntity entity = new RegionEntity();
                entity.setId(jsonEntity.getId());
                entity.setName(jsonEntity.getName());
                entity.setCountryEntity((CountryEntity) result.getValue().get(jsonEntity.getCountryId()));

                storage.put(jsonEntity.getId(), entity);

                return Optional.empty();
            }

            return Optional.of(Codes.ENTITY_CONVERSION_FAIL);
        };
    }

    private ConversionTask.Strategy createCityFillingStrategy() {
        return (storage, value, manager) -> {
            CityJsonEntity jsonEntity = (CityJsonEntity) value;

            Result<ObjectStorage> result = manager.get(Entities.REGIONS, Properties.JSON_TO_DB_CONVERSION_RESULT, ObjectStorage.class);
            if (result.isSuccess() && result.getValue().containsKey(jsonEntity.getRegionId())){
                CityEntity entity = new CityEntity();
                entity.setId(jsonEntity.getId());
                entity.setName(jsonEntity.getName());
                entity.setRegionEntity((RegionEntity) result.getValue().get(jsonEntity.getRegionId()));

                storage.put(jsonEntity.getId(), entity);

                return Optional.empty();
            }

            return Optional.of(Codes.ENTITY_CONVERSION_FAIL);
        };
    }

    private ConversionTask.Strategy createStreetFillingStrategy() {
        return (storage, value, manager) -> {
            StreetJsonEntity jsonEntity = (StreetJsonEntity) value;

            Result<ObjectStorage> result = manager.get(Entities.CITIES, Properties.JSON_TO_DB_CONVERSION_RESULT, ObjectStorage.class);
            if (result.isSuccess() && result.getValue().containsKey(jsonEntity.getCityId())){
                StreetEntity entity = new StreetEntity();
                entity.setId(jsonEntity.getId());
                entity.setName(jsonEntity.getName());
                entity.setCityEntity((CityEntity) result.getValue().get(jsonEntity.getCityId()));

                storage.put(jsonEntity.getId(), entity);

                return Optional.empty();
            }

            return Optional.of(Codes.ENTITY_CONVERSION_FAIL);
        };
    }

    private ConversionTask.Strategy createAddressFillingStrategy() {
        return (storage, value, manager) -> {
            AddressJsonEntity jsonEntity = (AddressJsonEntity) value;

            Result<ObjectStorage> result = manager.get(Entities.STREETS, Properties.JSON_TO_DB_CONVERSION_RESULT, ObjectStorage.class);
            if (result.isSuccess() && result.getValue().containsKey(jsonEntity.getStreetId())){
                AddressEntity entity = new AddressEntity();
                entity.setId(jsonEntity.getId());
                entity.setName(jsonEntity.getName());
                entity.setStreetEntity((StreetEntity) result.getValue().get(jsonEntity.getStreetId()));

                storage.put(jsonEntity.getId(), entity);

                return Optional.empty();
            }

            return Optional.of(Codes.ENTITY_CONVERSION_FAIL);
        };
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
