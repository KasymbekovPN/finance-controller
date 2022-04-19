package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.initialization.generators.valued.*;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.storage.ObjectStorage;
import kpn.financecontroller.initialization.tasks.testUtils.TestJsonEntity;
import kpn.financecontroller.initialization.tasks.testUtils.TestKeys;
import kpn.financecontroller.initialization.tasks.testUtils.TestManagerCreator;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.contexts.DefaultContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class ConversionTaskTest {

    private static final Valued<String> KEY = TestKeys.KEY;
    private static final ValuedGenerator<String> VALUED_GENERATOR = new ValuedStringGenerator();
    private static final Function<Context, ResultContextManager> CREATOR = new TestManagerCreator();
    private static final Long ENTITY_ID = 1L;

    private static Result<ObjectStorage> expectedResult_ifNoJsonObj;
    private static Result<ObjectStorage> expectedResult_ifEntityNotExist;
    private static Result<ObjectStorage> expectedResult_ifEntityConversionFail;
    private static Result<ObjectStorage> expectedResult;

    @BeforeAll
    static void beforeAll() {
        expectedResult_ifNoJsonObj = ImmutableResult.<ObjectStorage>fail(VALUED_GENERATOR.generate(KEY, Codes.NO_JSON_OBJECT))
                .value(new ObjectStorage())
                .arg(KEY)
                .build();
        expectedResult_ifEntityNotExist = ImmutableResult.<ObjectStorage>fail(VALUED_GENERATOR.generate(KEY, Codes.ENTITY_NOT_EXIST_ON_CONVERSION))
                .value(new ObjectStorage())
                .arg(KEY)
                .build();
        expectedResult_ifEntityConversionFail = ImmutableResult.<ObjectStorage>fail(VALUED_GENERATOR.generate(KEY, Codes.ENTITY_CONVERSION_FAIL))
                .value(new ObjectStorage())
                .arg(KEY)
                .build();

        expectedResult = ImmutableResult.<ObjectStorage>ok(new ObjectStorage())
                .arg(KEY)
                .build();
    }

    @Test
    void shouldCheckExecution_ifJsonObjNotExist() {
        ConversionTask task = createTask(true);

        Context context = new ContextBuilder().build();
        task.execute(context);

        Result<ObjectStorage> result = CREATOR.apply(context).get(KEY, Properties.JSON_TO_DB_CONVERSION_RESULT, ObjectStorage.class);
        assertThat(expectedResult_ifNoJsonObj).isEqualTo(result);
    }

    @Test
    void shouldCheckExecution_ifEntityNotExist() {
        Context context = new ContextBuilder()
                .addJsonObject()
                .build();
        ConversionTask task = createTask(true);
        task.execute(context);

        Result<ObjectStorage> result = CREATOR.apply(context).get(KEY, Properties.JSON_TO_DB_CONVERSION_RESULT, ObjectStorage.class);
        assertThat(expectedResult_ifEntityNotExist).isEqualTo(result);
    }

    @Test
    void shouldCheckExecution_ifEntityConversionFail() {
        Context context = new ContextBuilder()
                .addJsonObject()
                .addEntity(ENTITY_ID)
                .build();
        ConversionTask task = createTask(false);
        task.execute(context);

        Result<ObjectStorage> result = CREATOR.apply(context).get(KEY, Properties.JSON_TO_DB_CONVERSION_RESULT, ObjectStorage.class);
        assertThat(expectedResult_ifEntityConversionFail).isEqualTo(result);
    }

    @Test
    void shouldCheckExecution() {
        Context context = new ContextBuilder()
                .addJsonObject()
                .addEntity(ENTITY_ID)
                .build();

        ConversionTask task = createTask(true);
        task.execute(context);

        Result<ObjectStorage> result = CREATOR.apply(context).get(KEY, Properties.JSON_TO_DB_CONVERSION_RESULT, ObjectStorage.class);
        assertThat(expectedResult).isEqualTo(result);
    }

    private ConversionTask createTask(boolean success) {
        ConversionTask task = new ConversionTask();
        task.setKey(KEY);
        task.setValuedGenerator(VALUED_GENERATOR);
        task.setManagerCreator(CREATOR);
        task.setEntityId(ENTITY_ID);
        task.setStrategy((storage, jsonEntity, manager) -> {
            return success ? Optional.empty() : Optional.of(Codes.ENTITY_CONVERSION_FAIL);
        });

        return task;
    }

    private static class ContextBuilder{
        private final Context context;
        private ObjectStorage storage;

        public ContextBuilder() {
            this.context = new DefaultContext();
        }

        public ContextBuilder addJsonObject() {
            storage = new ObjectStorage();
            return this;
        }

        public ContextBuilder addEntity(Long id) {
            if (storage != null){
                TestJsonEntity entity = new TestJsonEntity();
                entity.setId(id);
                storage.put(id, entity);
            }
            return this;
        }

        public Context build(){
            if (storage != null){
                Result<ObjectStorage> result = ImmutableResult.<ObjectStorage>ok(storage).build();
                CREATOR.apply(context).put(TestKeys.KEY, Properties.JSON_OBJECT_CREATION_RESULT, result);
            }
            return context;
        }
    }
}