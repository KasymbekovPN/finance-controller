package kpn.financecontroller.initialization.tasks.conversion;

import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.initialization.entities.RegionJsonEntity;
import kpn.financecontroller.initialization.generators.valued.*;
import kpn.financecontroller.initialization.jsonObjs.RegionLongKeyJsonObj;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.storages.CountryStorage;
import kpn.financecontroller.initialization.storages.RegionStorage;
import kpn.financecontroller.initialization.tasks.conversion.RegionConversionTask;
import kpn.financecontroller.initialization.tasks.testUtils.TestKeys;
import kpn.financecontroller.initialization.tasks.testUtils.TestManagerCreator;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.contexts.DefaultContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class RegionConversionTaskTest {

    private static final Valued<String> KEY = TestKeys.KEY;
    private static final ValuedGenerator<String> VALUED_GENERATOR = new ValuedStringGenerator();
    private static final Function<Context, ResultContextManager> CREATOR = new TestManagerCreator();
    private static final Long REGION_ID = 1L;
    private static final String REGION_NAME = "name";
    private static final Long COUNTRY_ID = 123L;
    private static final String COUNTRY_NAME = "country.name";

    private static Result<RegionStorage> expectedResult_ifNoJsonObj;
    private static Result<RegionStorage> expectedResult_ifEntityNotExist;
    private static Result<RegionStorage> expectedResult_ifEntityConversionFail;
    private static Result<RegionStorage> expectedResult;

    @BeforeAll
    static void beforeAll() {
        expectedResult_ifNoJsonObj = ImmutableResult.<RegionStorage>builder()
                .success(false)
                .code(VALUED_GENERATOR.generate(KEY, Codes.NO_JSON_OBJECT))
                .value(new RegionStorage())
                .arg(KEY)
                .build();
        expectedResult_ifEntityNotExist = ImmutableResult.<RegionStorage>builder()
                .success(false)
                .code(VALUED_GENERATOR.generate(KEY, Codes.ENTITY_NOT_EXIST_ON_CONVERSION))
                .value(new RegionStorage())
                .arg(KEY)
                .build();
        expectedResult_ifEntityConversionFail = ImmutableResult.<RegionStorage>builder()
                .success(false)
                .code(VALUED_GENERATOR.generate(KEY, Codes.ENTITY_CONVERSION_FAIL))
                .value(new RegionStorage())
                .arg(KEY)
                .build();

        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setId(COUNTRY_ID);
        countryEntity.setName(COUNTRY_NAME);

        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setId(REGION_ID);
        regionEntity.setName(REGION_NAME);
        regionEntity.setCountryEntity(countryEntity);

        RegionStorage storage = new RegionStorage();
        storage.put(REGION_ID, regionEntity);

        expectedResult = ImmutableResult.<RegionStorage>builder()
                .success(true)
                .value(storage)
                .arg(KEY)
                .build();
    }

    @Test
    void shouldCheckExecution_ifJsonObjNotExist() {
        RegionConversionTask task = createTask();

        Context context = new ContextBuilder().build();
        task.execute(context);

        Result<RegionStorage> result = CREATOR.apply(context).get(KEY, Properties.JSON_TO_DB_CONVERSION_RESULT, RegionStorage.class);
        assertThat(expectedResult_ifNoJsonObj).isEqualTo(result);
    }

    @Test
    void shouldCheckExecution_ifEntityNotExist() {
        Context context = new ContextBuilder()
                .addJsonObject()
                .build();
        RegionConversionTask task = createTask();
        task.execute(context);

        Result<RegionStorage> result = CREATOR.apply(context).get(KEY, Properties.JSON_TO_DB_CONVERSION_RESULT, RegionStorage.class);
        assertThat(expectedResult_ifEntityNotExist).isEqualTo(result);
    }

    @Test
    void shouldCheckExecution_ifEntityConversionFail() {
        Context context = new ContextBuilder()
                .addJsonObject()
                .addEntity(REGION_ID, REGION_NAME, COUNTRY_ID)
                .build();
        RegionConversionTask task = createTask();
        task.execute(context);

        Result<RegionStorage> result = CREATOR.apply(context).get(KEY, Properties.JSON_TO_DB_CONVERSION_RESULT, RegionStorage.class);
        assertThat(expectedResult_ifEntityConversionFail).isEqualTo(result);
    }

    @Test
    void shouldCheckExecution() {
        Context context = new ContextBuilder()
                .addJsonObject()
                .addEntity(REGION_ID, REGION_NAME, COUNTRY_ID)
                .addCountryStorage()
                .build();

        RegionConversionTask task = createTask();
        task.execute(context);

        Result<RegionStorage> result = CREATOR.apply(context).get(KEY, Properties.JSON_TO_DB_CONVERSION_RESULT, RegionStorage.class);
        assertThat(expectedResult).isEqualTo(result);
    }

    private RegionConversionTask createTask() {
        RegionConversionTask task = new RegionConversionTask();
        task.setKey(KEY);
        task.setValuedGenerator(VALUED_GENERATOR);
        task.setManagerCreator(CREATOR);
        task.setEntityId(REGION_ID);

        return task;
    }

    private static class ContextBuilder{
        private final Context context;
        private RegionLongKeyJsonObj jsonObject;
        private CountryStorage countryStorage;

        public ContextBuilder() {
            this.context = new DefaultContext();
        }

        public ContextBuilder addJsonObject() {
            jsonObject = new RegionLongKeyJsonObj();
            jsonObject.setEntities(new HashMap<>());
            return this;
        }

        public ContextBuilder addEntity(Long regionId, String regionName, Long countryId) {
            if (jsonObject != null){
                RegionJsonEntity entity = new RegionJsonEntity();
                entity.setId(regionId);
                entity.setName(regionName);
                entity.setCountryId(countryId);
                jsonObject.getEntities().put(regionId, entity);
            }
            return this;
        }

        public ContextBuilder addCountryStorage() {
            countryStorage = new CountryStorage();
            CountryEntity countryEntity = new CountryEntity();
            countryEntity.setId(COUNTRY_ID);
            countryEntity.setName(COUNTRY_NAME);
            countryStorage.put(COUNTRY_ID, countryEntity);
            return this;
        }

        public Context build(){
            if (jsonObject != null){
                Result<RegionLongKeyJsonObj> result = ImmutableResult.<RegionLongKeyJsonObj>ok(jsonObject).build();
                CREATOR.apply(context).put(TestKeys.KEY, Properties.JSON_OBJECT_CREATION_RESULT, result);
            }
            if (countryStorage != null){
                ImmutableResult<CountryStorage> result = ImmutableResult.<CountryStorage>ok(countryStorage).build();
                CREATOR.apply(context).put(Entities.COUNTRIES, Properties.JSON_TO_DB_CONVERSION_RESULT, result);
            }
            return context;
        }
    }
}