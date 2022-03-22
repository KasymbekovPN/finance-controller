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

import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonToDbConversionTagTaskTest {

    private static final Valued<String> KEY = TestKeys.KEY;
    private static final ValuedGenerator<String> VALUED_GENERATOR = new ValuedStringGenerator();
    private static final Function<Context, ResultContextManager> CREATOR = new TestManagerCreator();
    private static final Long ENTITY_ID = 1L;
    private static final String ENTITY_NAME = "name";

    private static Result<TagStorage> expectedResult_ifNoJsonObj;
    private static Result<TagStorage> expectedResult;


    @BeforeAll
    static void beforeAll() {
        expectedResult_ifNoJsonObj = Result.<TagStorage>builder()
                .success(false)
                .code(VALUED_GENERATOR.generate(KEY, Codes.NO_JSON_OBJECT))
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
        JsonToDbConversionTagTask task = new JsonToDbConversionTagTask(KEY, VALUED_GENERATOR, CREATOR);
        SimpleContext context = new SimpleContext();
        task.execute(context);

        Result<TagStorage> result = CREATOR.apply(context).get(KEY, Properties.JSON_TO_DB_CONVERSION_RESULT, TagStorage.class);
        assertThat(expectedResult_ifNoJsonObj).isEqualTo(result);
    }

    @Test
    void shouldCheckExecution() {
        TagJsonEntity tagJsonEntity = new TagJsonEntity();
        tagJsonEntity.setId(ENTITY_ID);
        tagJsonEntity.setName(ENTITY_NAME);

        TagLongKeyJsonObj jsonObj = new TagLongKeyJsonObj();
        jsonObj.setEntities(Map.of(ENTITY_ID, tagJsonEntity));

        Result<TagLongKeyJsonObj> objResult = Result.<TagLongKeyJsonObj>builder()
                .success(true)
                .value(jsonObj)
                .build();
        SimpleContext context = new SimpleContext();
        CREATOR.apply(context).put(KEY, Properties.JSON_OBJECT_CREATION_RESULT, objResult);

        JsonToDbConversionTagTask task = new JsonToDbConversionTagTask(KEY, VALUED_GENERATOR, CREATOR);
        task.execute(context);

        Result<TagStorage> result = CREATOR.apply(context).get(KEY, Properties.JSON_TO_DB_CONVERSION_RESULT, TagStorage.class);
        assertThat(expectedResult).isEqualTo(result);
    }
}
