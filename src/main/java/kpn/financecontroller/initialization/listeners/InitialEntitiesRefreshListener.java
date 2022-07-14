package kpn.financecontroller.initialization.listeners;

import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.data.domains.seller.Seller;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.address.AddressEntity;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.entities.payment.PaymentEntity;
import kpn.financecontroller.data.entities.seller.SellerEntity;
import kpn.financecontroller.data.entities.product.ProductEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.data.services.dto.DTOServiceOLdOld;
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
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Component
public class InitialEntitiesRefreshListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private DTOServiceOLdOld<Tag, TagEntity> tagDtoServiceOLd;
    @Autowired
    private DTOServiceOLdOld<Country, CountryEntity> countryDtoServiceOLd;
    @Autowired
    private DTOServiceOLdOld<Region, RegionEntity> regionDtoServiceOLd;
    @Autowired
    private DTOServiceOLdOld<City, CityEntity> cityDtoServiceOLd;
    @Autowired
    private DTOServiceOLdOld<Street, StreetEntity> streetDtoServiceOLd;
    @Autowired
    private DTOServiceOLdOld<Address, AddressEntity> addressDtoServiceOLd;
    @Autowired
    private DTOServiceOLdOld<Seller, SellerEntity> sellerDtoServiceOLd;
    @Autowired
    private DTOServiceOLdOld<Product, ProductEntity> productDtoServiceOLd;
    @Autowired
    private DTOServiceOLdOld<Payment, PaymentEntity> paymentDtoServiceOLd;

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
                            Entities.ADDRESSES,
                            Entities.SELLERS,
                            Entities.PRODUCTS,
                            Entities.PAYMENTS
            ));
            executor.addGenerator(readingGenerator);

            Generator creationGenerator = createCreationGenerator(List.of(
                    new Pair<>(Entities.TAGS, new CreationTask.ObjectStorageCreator(TagLongKeyJsonObj.class)),
                    new Pair<>(Entities.COUNTRIES, new CreationTask.ObjectStorageCreator(CountryLongKeyJsonObj.class)),
                    new Pair<>(Entities.REGIONS, new CreationTask.ObjectStorageCreator(RegionLongKeyJsonObj.class)),
                    new Pair<>(Entities.CITIES, new CreationTask.ObjectStorageCreator(CityLongKeyJsonObj.class)),
                    new Pair<>(Entities.STREETS, new CreationTask.ObjectStorageCreator(StreetLongKeyJsonObj.class)),
                    new Pair<>(Entities.ADDRESSES, new CreationTask.ObjectStorageCreator(AddressLongKeyJsonObj.class)),
                    new Pair<>(Entities.SELLERS, new CreationTask.ObjectStorageCreator(SellerLongKeyJsonObj.class)),
                    new Pair<>(Entities.PRODUCTS, new CreationTask.ObjectStorageCreator(ProductLongKeyJsonObj.class)),
                    new Pair<>(Entities.PAYMENTS, new CreationTask.ObjectStorageCreator(PaymentLongKeyJsonObj.class))
            ));
            executor.addGenerator(creationGenerator);

            Generator cleanupGenerator = createCleanupGenerator(List.of(
                    new Pair<>(Entities.PAYMENTS, paymentDtoServiceOLd),
                    new Pair<>(Entities.PRODUCTS, productDtoServiceOLd),
                    new Pair<>(Entities.SELLERS, sellerDtoServiceOLd),
                    new Pair<>(Entities.ADDRESSES, addressDtoServiceOLd),
                    new Pair<>(Entities.STREETS, streetDtoServiceOLd),
                    new Pair<>(Entities.CITIES, cityDtoServiceOLd),
                    new Pair<>(Entities.REGIONS, regionDtoServiceOLd),
                    new Pair<>(Entities.COUNTRIES, countryDtoServiceOLd),
                    new Pair<>(Entities.TAGS, tagDtoServiceOLd)
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
                    .addGenerator(createSavingGenerator(Entities.ADDRESSES, createAddressSavingStrategy()))

                    .addGenerator(createConversionGenerator(Entities.SELLERS, createSellerFillingStrategy()))
                    .addGenerator(createSavingGenerator(Entities.SELLERS, createSellerSavingStrategy()))

                    .addGenerator(createConversionGenerator(Entities.PRODUCTS, createProductFillingStrategy()))
                    .addGenerator(createSavingGenerator(Entities.PRODUCTS, createProductSavingStrategy()))

                    .addGenerator(createConversionGenerator(Entities.PAYMENTS, createPaymentFillingStrategy()))
                    .addGenerator(createSavingGenerator(Entities.PAYMENTS, createPaymentSavingStrategy()));

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

    private Generator createCleanupGenerator(List<Pair<Valued<String>, DTOServiceOLdOld<?, ?>>> init) {
        CleanupGenerator.Builder builder = CleanupGenerator.builder();
        for (Pair<Valued<String>, DTOServiceOLdOld<?, ?>> item : init) {
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
            Result<Tag> result = tagDtoServiceOLd.saver().save(entity);
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
            Result<Country> result = countryDtoServiceOLd.saver().save(entity);
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
            Result<Region> result = regionDtoServiceOLd.saver().save(entity);
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
            Result<City> result = cityDtoServiceOLd.saver().save(entity);
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
            Result<Street> result = streetDtoServiceOLd.saver().save(entity);
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
            Result<Address> result = addressDtoServiceOLd.saver().save(entity);
            if (result.isSuccess()){
                entity.setId(result.getValue().getId());
                return Optional.empty();
            }
            return Optional.of(Codes.FAIL_SAVING_ATTEMPT);
        };
    }

    private SavingTask.Strategy createSellerSavingStrategy() {
        return value -> {
            SellerEntity entity = (SellerEntity) value;
            Result<Seller> result = sellerDtoServiceOLd.saver().save(entity);
            if (result.isSuccess()){
                entity.setId(result.getValue().getId());
                return Optional.empty();
            }
            return Optional.of(Codes.FAIL_SAVING_ATTEMPT);
        };
    }

    private SavingTask.Strategy createProductSavingStrategy() {
        return value -> {
            ProductEntity entity = (ProductEntity) value;
            Result<Product> result = productDtoServiceOLd.saver().save(entity);
            if (result.isSuccess()){
                entity.setId(result.getValue().getId());
                return Optional.empty();
            }
            return Optional.of(Codes.FAIL_SAVING_ATTEMPT);
        };
    }

    private SavingTask.Strategy createPaymentSavingStrategy() {
        return value -> {
            PaymentEntity entity = (PaymentEntity) value;
            Result<Payment> result = paymentDtoServiceOLd.saver().save(entity);
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

    private ConversionTask.Strategy createSellerFillingStrategy() {
        return (storage, value, manager) -> {
            SellerJsonEntity jsonEntity = (SellerJsonEntity) value;

            SellerEntity entity = new SellerEntity();
            entity.setId(jsonEntity.getId());
            entity.setName(jsonEntity.getName());
            entity.setUrl(jsonEntity.getUrl());
            entity.setDescription(jsonEntity.getDescription());

            if (jsonEntity.getAddressId() != null){
                Result<ObjectStorage> result = manager.get(Entities.ADDRESSES, Properties.JSON_TO_DB_CONVERSION_RESULT, ObjectStorage.class);
                if (!result.isSuccess() || !result.getValue().containsKey(jsonEntity.getAddressId())){
                    return Optional.of(Codes.ENTITY_CONVERSION_FAIL);
                }

                entity.setAddressEntity((AddressEntity) result.getValue().get(jsonEntity.getAddressId()));
            }

            storage.put(jsonEntity.getId(), entity);
            return Optional.empty();
        };
    }

    private ConversionTask.Strategy createProductFillingStrategy() {
        return (storage, value, manager) -> {
            ProductJsonEntity jsonEntity = (ProductJsonEntity) value;

            ProductEntity entity = new ProductEntity();
            entity.setId(jsonEntity.getId());
            entity.setName(jsonEntity.getName());

            HashSet<TagEntity> tagEntities = new HashSet<>();
            Result<ObjectStorage> result = manager.get(Entities.TAGS, Properties.JSON_TO_DB_CONVERSION_RESULT, ObjectStorage.class);
            if (result.isSuccess()){
                ObjectStorage objectStorage = result.getValue();
                for (Long tagId : jsonEntity.getTags()) {
                    if (objectStorage.containsKey(tagId)){
                        tagEntities.add((TagEntity) objectStorage.get(tagId));
                    } else {
                        return Optional.of(Codes.ENTITY_CONVERSION_FAIL);
                    }
                }
            }

            entity.setTagEntities(tagEntities);
            storage.put(jsonEntity.getId(), entity);
            return Optional.empty();
        };
    }

    private ConversionTask.Strategy createPaymentFillingStrategy() {
        return (storage, value, manager) -> {
            PaymentJsonEntity jsonEntity = (PaymentJsonEntity) value;

            Result<ObjectStorage> sellerResult = manager.get(Entities.SELLERS, Properties.JSON_TO_DB_CONVERSION_RESULT, ObjectStorage.class);
            Result<ObjectStorage> productResult = manager.get(Entities.PRODUCTS, Properties.JSON_TO_DB_CONVERSION_RESULT, ObjectStorage.class);
            Long sellerId = jsonEntity.getSellerId();
            Long productId = jsonEntity.getProductId();
            if (!sellerResult.isSuccess() ||
                !productResult.isSuccess() ||
                !sellerResult.getValue().containsKey(sellerId) ||
                !productResult.getValue().containsKey(productId)){
                return Optional.of(Codes.ENTITY_CONVERSION_FAIL);
            }

            PaymentEntity entity = new PaymentEntity();
            entity.setId(jsonEntity.getId());
            entity.setAmount(jsonEntity.getAmount());
            entity.setMeasure(jsonEntity.getMeasure());
            entity.setPrice(jsonEntity.getPrice());
            entity.setCurrency(jsonEntity.getCurrency());
            entity.setCreatedAt(jsonEntity.getCreatedAt());
            entity.setSellerEntity((SellerEntity) sellerResult.getValue().get(sellerId));
            entity.setProductEntity((ProductEntity) productResult.getValue().get(productId));

            storage.put(jsonEntity.getId(), entity);
            return Optional.empty();
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
