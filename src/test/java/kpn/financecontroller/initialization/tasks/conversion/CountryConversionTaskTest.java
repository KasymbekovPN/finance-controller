package kpn.financecontroller.initialization.tasks.conversion;

import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.initialization.entities.CountryJsonEntity;
import kpn.financecontroller.initialization.generators.valued.*;
import kpn.financecontroller.initialization.jsonObjs.CountryLongKeyJsonObj;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.storage.CountryStorage;
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

class CountryConversionTaskTest {

    private static final Valued<String> KEY = TestKeys.KEY;
    private static final ValuedGenerator<String> VALUED_GENERATOR = new ValuedStringGenerator();
    private static final Function<Context, ResultContextManager> CREATOR = new TestManagerCreator();
    private static final Long ENTITY_ID = 1L;
    private static final String ENTITY_NAME = "name";

    private static Result<CountryStorage> expectedResult_ifNoJsonObj;
    private static Result<CountryStorage> expectedResult_ifEntityNotExist;
    private static Result<CountryStorage> expectedResult;

    @BeforeAll
    static void beforeAll() {
        expectedResult_ifNoJsonObj = ImmutableResult.<CountryStorage>builder()
                .success(false)
                .code(VALUED_GENERATOR.generate(KEY, Codes.NO_JSON_OBJECT))
                .value(new CountryStorage())
                .arg(KEY)
                .build();
        expectedResult_ifEntityNotExist = ImmutableResult.<CountryStorage>builder()
                .success(false)
                .code(VALUED_GENERATOR.generate(KEY, Codes.ENTITY_NOT_EXIST_ON_CONVERSION))
                .value(new CountryStorage())
                .arg(KEY)
                .build();

        CountryEntity entity = new CountryEntity();
        entity.setId(ENTITY_ID);
        entity.setName(ENTITY_NAME);

        CountryStorage storage = new CountryStorage();
        storage.put(ENTITY_ID, entity);

        expectedResult = ImmutableResult.<CountryStorage>builder()
                .success(true)
                .value(storage)
                .arg(KEY)
                .build();
    }

    @Test
    void shouldCheckExecution_ifJsonObjNotExist() {
        CountryConversionTask task = createTask();

        Context context = new ContextBuilder().build();
        task.execute(context);

        Result<CountryStorage> result = CREATOR.apply(context).get(KEY, Properties.JSON_TO_DB_CONVERSION_RESULT, CountryStorage.class);
        assertThat(expectedResult_ifNoJsonObj).isEqualTo(result);
    }

    @Test
    void shouldCheckExecution_ifEntityNotExist() {
        Context context = new ContextBuilder()
                .addJsonObject()
                .build();
        CountryConversionTask task = createTask();
        task.execute(context);

        Result<CountryStorage> result = CREATOR.apply(context).get(KEY, Properties.JSON_TO_DB_CONVERSION_RESULT, CountryStorage.class);
        assertThat(expectedResult_ifEntityNotExist).isEqualTo(result);
    }

    @Test
    void shouldCheckExecution() {
        Context context = new ContextBuilder()
                .addJsonObject()
                .addEntity(ENTITY_ID, ENTITY_NAME)
                .build();

        CountryConversionTask task = createTask();
        task.execute(context);

        Result<CountryStorage> result = CREATOR.apply(context).get(KEY, Properties.JSON_TO_DB_CONVERSION_RESULT, CountryStorage.class);
        assertThat(expectedResult).isEqualTo(result);
    }

    private CountryConversionTask createTask() {
        CountryConversionTask task = new CountryConversionTask();
        task.setKey(KEY);
        task.setValuedGenerator(VALUED_GENERATOR);
        task.setManagerCreator(CREATOR);
        task.setEntityId(ENTITY_ID);

        return task;
    }

    private static class ContextBuilder{
        private final Context context;
        private CountryLongKeyJsonObj jsonObject;

        public ContextBuilder() {
            this.context = new DefaultContext();
        }

        public ContextBuilder addJsonObject() {
            jsonObject = new CountryLongKeyJsonObj();
            jsonObject.setEntities(new HashMap<>());
            return this;
        }

        public ContextBuilder addEntity(Long id, String name){
            if (jsonObject != null){
                CountryJsonEntity entity = new CountryJsonEntity();
                entity.setId(id);
                entity.setName(name);
                jsonObject.getEntities().put(id, entity);
            }
            return this;
        }

        public Context build(){
            if (jsonObject != null){
                Result<CountryLongKeyJsonObj> result = ImmutableResult.<CountryLongKeyJsonObj>ok(jsonObject).build();
                CREATOR.apply(context).put(TestKeys.KEY, Properties.JSON_OBJECT_CREATION_RESULT, result);
            }
            return context;
        }
    }
}