package kpn.financecontroller.initialization.tasks.conversion;

import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.initialization.entities.TagJsonEntity;
import kpn.financecontroller.initialization.generators.valued.*;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.storage.ObjectStorage;
import kpn.financecontroller.initialization.tasks.testUtils.TestKeys;
import kpn.financecontroller.initialization.tasks.testUtils.TestManagerCreator;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.contexts.DefaultContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class TagConversionTaskTest {

    private static final Valued<String> KEY = TestKeys.KEY;
    private static final ValuedGenerator<String> VALUED_GENERATOR = new ValuedStringGenerator();
    private static final Function<Context, ResultContextManager> CREATOR = new TestManagerCreator();
    private static final Long ENTITY_ID = 1L;
    private static final String ENTITY_NAME = "name";

    private static Result<ObjectStorage> expectedResult_ifNoJsonObj;
    private static Result<ObjectStorage> expectedResult_ifEntityNotExist;
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

        TagEntity tagEntity = new TagEntity();
        tagEntity.setId(ENTITY_ID);
        tagEntity.setName(ENTITY_NAME);

        ObjectStorage storage = new ObjectStorage();
        storage.put(ENTITY_ID, tagEntity);

        expectedResult = ImmutableResult.<ObjectStorage>ok(storage)
                .arg(KEY)
                .build();
    }

    @Test
    void shouldCheckExecution_ifJsonObjNotExist() {
        TagConversionTask task = createTask();

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
        TagConversionTask task = createTask();
        task.execute(context);

        Result<ObjectStorage> result = CREATOR.apply(context).get(KEY, Properties.JSON_TO_DB_CONVERSION_RESULT, ObjectStorage.class);
        assertThat(expectedResult_ifEntityNotExist).isEqualTo(result);
    }

    @Test
    void shouldCheckExecution() {
        Context context = new ContextBuilder()
                .addJsonObject()
                .addEntity(ENTITY_ID, ENTITY_NAME)
                .build();

        TagConversionTask task = createTask();
        task.execute(context);

        Result<ObjectStorage> result = CREATOR.apply(context).get(KEY, Properties.JSON_TO_DB_CONVERSION_RESULT, ObjectStorage.class);
        assertThat(expectedResult).isEqualTo(result);
    }

    private TagConversionTask createTask() {
        TagConversionTask task = new TagConversionTask();
        task.setKey(KEY);
        task.setValuedGenerator(VALUED_GENERATOR);
        task.setManagerCreator(CREATOR);
        task.setEntityId(ENTITY_ID);

        return task;
    }

    private static class ContextBuilder{
        private final Context context;
        private ObjectStorage jsonObjectStorage;

        public ContextBuilder() {
            this.context = new DefaultContext();
        }

        public ContextBuilder addJsonObject() {
            jsonObjectStorage = new ObjectStorage();
            return this;
        }

        public ContextBuilder addEntity(Long id, String name){
            if (jsonObjectStorage != null){
                TagJsonEntity entity = new TagJsonEntity();
                entity.setId(id);
                entity.setName(name);
                jsonObjectStorage.put(id, entity);
            }
            return this;
        }

        public Context build(){
            if (jsonObjectStorage != null){
                Result<ObjectStorage> result = ImmutableResult.<ObjectStorage>builder()
                        .success(true)
                        .value(jsonObjectStorage)
                        .build();
                CREATOR.apply(context).put(TestKeys.KEY, Properties.JSON_OBJECT_CREATION_RESULT, result);
            }
            return context;
        }
    }
}
