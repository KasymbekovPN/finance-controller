package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.initialization.entities.TagJsonEntity;
import kpn.financecontroller.initialization.generators.valued.*;
import kpn.financecontroller.initialization.jsonObjs.TagLongKeyJsonObj;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.storages.TagStorage;
import kpn.financecontroller.initialization.tasks.testUtils.TestKeys;
import kpn.financecontroller.initialization.tasks.testUtils.TestManagerCreator;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.contexts.SimpleContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonToDbConversionTagTaskTest {

    private static final Valued<String> KEY = TestKeys.KEY;
    private static final ValuedGenerator<String> VALUED_GENERATOR = new ValuedStringGenerator();
    private static final Function<Context, ResultContextManager> CREATOR = new TestManagerCreator();
    private static final Long ENTITY_ID = 1L;
    private static final String ENTITY_NAME = "name";

    private static Result<TagStorage> expectedResult_ifNoJsonObj;
    private static Result<TagStorage> expectedResult_ifEntityNotExist;
    private static Result<TagStorage> expectedResult;

    @BeforeAll
    static void beforeAll() {
        expectedResult_ifNoJsonObj = Result.<TagStorage>builder()
                .success(false)
                .code(VALUED_GENERATOR.generate(KEY, Codes.NO_JSON_OBJECT))
                .value(new TagStorage())
                .arg(KEY)
                .build();
        expectedResult_ifEntityNotExist = Result.<TagStorage>builder()
                .success(false)
                .code(VALUED_GENERATOR.generate(KEY, Codes.ENTITY_NOT_EXIST_ON_CONVERSION))
                .value(new TagStorage())
                .arg(KEY)
                .build();

        TagEntity tagEntity = new TagEntity();
        tagEntity.setId(ENTITY_ID);
        tagEntity.setName(ENTITY_NAME);

        TagStorage tagStorage = new TagStorage();
        tagStorage.put(ENTITY_ID, tagEntity);

        expectedResult = Result.<TagStorage>builder()
                .success(true)
                .value(tagStorage)
                .arg(KEY)
                .build();
    }

    @Test
    void shouldCheckExecution_ifJsonObjNotExist() {
        JsonToDbConversionTagTask task = createTask();

        Context context = new ContextBuilder().build();
        task.execute(context);

        Result<TagStorage> result = CREATOR.apply(context).get(KEY, Properties.JSON_TO_DB_CONVERSION_RESULT, TagStorage.class);
        assertThat(expectedResult_ifNoJsonObj).isEqualTo(result);
    }

    @Test
    void shouldCheckExecution_ifEntityNotExist() {
        Context context = new ContextBuilder()
                .addJsonObject()
                .build();
        JsonToDbConversionTagTask task = createTask();
        task.execute(context);

        Result<TagStorage> result = CREATOR.apply(context).get(KEY, Properties.JSON_TO_DB_CONVERSION_RESULT, TagStorage.class);
        assertThat(expectedResult_ifEntityNotExist).isEqualTo(result);
    }

    @Test
    void shouldCheckExecution() {
        Context context = new ContextBuilder()
                .addJsonObject()
                .addEntity(ENTITY_ID, ENTITY_NAME)
                .build();

        JsonToDbConversionTagTask task = createTask();
        task.execute(context);

        Result<TagStorage> result = CREATOR.apply(context).get(KEY, Properties.JSON_TO_DB_CONVERSION_RESULT, TagStorage.class);
        assertThat(expectedResult).isEqualTo(result);
    }

    private JsonToDbConversionTagTask createTask() {
        JsonToDbConversionTagTask task = new JsonToDbConversionTagTask();
        task.setKey(KEY);
        task.setValuedGenerator(VALUED_GENERATOR);
        task.setManagerCreator(CREATOR);
        task.setEntityId(ENTITY_ID);

        return task;
    }

    private static class ContextBuilder{
        private final Context context;
        private TagLongKeyJsonObj jsonObject;

        public ContextBuilder() {
            this.context = new SimpleContext();
        }

        public ContextBuilder addJsonObject() {
            jsonObject = new TagLongKeyJsonObj();
            jsonObject.setEntities(new HashMap<>());
            return this;
        }

        public ContextBuilder addEntity(Long id, String name){
            if (jsonObject != null){
                TagJsonEntity entity = new TagJsonEntity();
                entity.setId(id);
                entity.setName(name);
                jsonObject.getEntities().put(id, entity);
            }
            return this;
        }

        public Context build(){
            if (jsonObject != null){
                Result<TagLongKeyJsonObj> result = Result.<TagLongKeyJsonObj>builder()
                        .success(true)
                        .value(jsonObject)
                        .build();
                CREATOR.apply(context).put(TestKeys.KEY, Properties.JSON_OBJECT_CREATION_RESULT, result);
            }
            return context;
        }
    }
}