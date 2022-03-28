package kpn.financecontroller.initialization.generators.seed;

import kpn.financecontroller.initialization.entities.TagJsonEntity;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.generators.valued.ValuedStringGenerator;
import kpn.financecontroller.initialization.jsonObjs.LongKeyJsonObj;
import kpn.financecontroller.initialization.jsonObjs.TagLongKeyJsonObj;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.tasks.testUtils.TestKeys;
import kpn.financecontroller.initialization.tasks.testUtils.TestManagerCreator;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.contexts.SimpleContext;
import kpn.taskexecutor.lib.seeds.Seed;
import kpn.taskexecutor.lib.tasks.Task;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class ConversionGeneratorTest {

    private static final Context CONTEXT = new SimpleContext();
    private static final Function<Context, ResultContextManager> CREATOR = new TestManagerCreator();
    private static final ValuedGenerator<String> VALUED_GENERATOR = new ValuedStringGenerator();
    private static final Valued<String> KEY = TestKeys.KEY;
    private static final Class<? extends Task> TYPE = Task.class;
    private static final Long ENTITY_ID = 1L;
    private static final Class<? extends LongKeyJsonObj<?>> JSON_OBJ_TYPE = TagLongKeyJsonObj.class;

    @Test
    void shouldCheckNextGetting_ifManagerCreatorNull() {
        ConversionGenerator seedGenerator = ConversionGenerator.builder().build();
        Optional<Seed> maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isEmpty();
    }

    @Test
    void shouldCheckNextGetting_ifValuedGeneratorNull() {
        ConversionGenerator seedGenerator = ConversionGenerator.builder()
                .managerCreator(CREATOR)
                .build();
        Optional<Seed> maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isEmpty();
    }

    @Test
    void shouldCheckNextGetting_ifKeyNull() {
        ConversionGenerator seedGenerator = ConversionGenerator.builder()
                .managerCreator(CREATOR)
                .valuedGenerator(VALUED_GENERATOR)
                .build();
        Optional<Seed> maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isEmpty();
    }

    @Test
    void shouldCheckNextGetting_ifTypeNull() {
        ConversionGenerator seedGenerator = ConversionGenerator.builder()
                .managerCreator(CREATOR)
                .valuedGenerator(VALUED_GENERATOR)
                .key(KEY)
                .build();
        Optional<Seed> maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isEmpty();
    }

    @Test
    void shouldCheckNextGetting_ifJsonObjTypeNull() {
        ConversionGenerator seedGenerator = ConversionGenerator.builder()
                .managerCreator(CREATOR)
                .valuedGenerator(VALUED_GENERATOR)
                .key(KEY)
                .type(TYPE)
                .build();
        Optional<Seed> maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isEmpty();
    }

    @Test
    void shouldCheckNextGetting_ifJsonObjectAbsent() {
        ConversionGenerator seedGenerator = ConversionGenerator.builder()
                .managerCreator(CREATOR)
                .valuedGenerator(VALUED_GENERATOR)
                .key(KEY)
                .type(TYPE)
                .jsonObj(JSON_OBJ_TYPE)
                .build();
        Optional<Seed> maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isEmpty();
    }

    @Test
    void shouldCheckNextGetting() {
        Map<String, Object> expectedFields = Map.of(
                "valuedGenerator", VALUED_GENERATOR,
                "managerCreator", CREATOR,
                "entityId", ENTITY_ID
        );

        TagJsonEntity entity = new TagJsonEntity();
        entity.setId(ENTITY_ID);
        entity.setName("name");

        TagLongKeyJsonObj jsonObj = new TagLongKeyJsonObj();
        jsonObj.setEntities(Map.of(ENTITY_ID, entity));

        Result<TagLongKeyJsonObj> result = Result.<TagLongKeyJsonObj>builder()
                .success(true)
                .value(jsonObj)
                .build();

        CREATOR.apply(CONTEXT).put(KEY, Properties.JSON_OBJECT_CREATION_RESULT, result);

        ConversionGenerator seedGenerator = ConversionGenerator.builder()
                .managerCreator(CREATOR)
                .valuedGenerator(VALUED_GENERATOR)
                .key(KEY)
                .type(TYPE)
                .jsonObj(JSON_OBJ_TYPE)
                .build();

        Optional<Seed> maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isPresent();
        Seed seed = maybeSeed.get();
        assertThat(seed.getType()).isEqualTo(TYPE);
        assertThat(seed.getFields()).isEqualTo(expectedFields);

        maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isEmpty();
    }
}
