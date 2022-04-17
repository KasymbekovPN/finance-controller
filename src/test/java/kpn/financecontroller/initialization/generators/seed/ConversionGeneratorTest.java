package kpn.financecontroller.initialization.generators.seed;

import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.generators.valued.ValuedStringGenerator;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.storage.ObjectStorage;
import kpn.financecontroller.initialization.tasks.ConversionTask;
import kpn.financecontroller.initialization.tasks.testUtils.TestJsonEntity;
import kpn.financecontroller.initialization.tasks.testUtils.TestKeys;
import kpn.financecontroller.initialization.tasks.testUtils.TestManagerCreator;
import kpn.lib.result.ImmutableResult;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.contexts.DefaultContext;
import kpn.taskexecutor.lib.seed.Seed;
import kpn.taskexecutor.lib.seed.generator.Generator;
import kpn.taskexecutor.lib.task.Task;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class ConversionGeneratorTest {

    private static final Context CONTEXT = new DefaultContext();
    private static final Function<Context, ResultContextManager> CREATOR = new TestManagerCreator();
    private static final ValuedGenerator<String> VALUED_GENERATOR = new ValuedStringGenerator();
    private static final Valued<String> KEY = TestKeys.KEY;
    private static final Class<? extends Task> TYPE = Task.class;
    private static final ConversionTask.ObjectStorageFiller OSF = (context, jsonEntity) -> {return Optional.empty();};
    private static final Long ENTITY_ID = 1L;

    @Test
    void shouldCheckNextGetting_ifManagerCreatorNull() {
        Generator seedGenerator = ConversionGenerator.builder().build();
        Optional<Seed> maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isEmpty();
    }

    @Test
    void shouldCheckNextGetting_ifValuedGeneratorNull() {
        Generator seedGenerator = ConversionGenerator.builder()
                .managerCreator(CREATOR)
                .build();
        Optional<Seed> maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isEmpty();
    }

    @Test
    void shouldCheckNextGetting_ifKeyNull() {
        Generator seedGenerator = ConversionGenerator.builder()
                .managerCreator(CREATOR)
                .valuedGenerator(VALUED_GENERATOR)
                .build();
        Optional<Seed> maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isEmpty();
    }

    @Test
    void shouldCheckNextGetting_ifTypeNull() {
        Generator seedGenerator = ConversionGenerator.builder()
                .managerCreator(CREATOR)
                .valuedGenerator(VALUED_GENERATOR)
                .key(KEY)
                .build();
        Optional<Seed> maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isEmpty();
    }

    @Test
    void shouldCheckNextGetting_ifObjectStorageFillerNull() {
        Generator seedGenerator = ConversionGenerator.builder()
                .type(TYPE)
                .managerCreator(CREATOR)
                .valuedGenerator(VALUED_GENERATOR)
                .key(KEY)
                .build();
        Optional<Seed> maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isEmpty();
    }

    @Test
    void shouldCheckNextGetting_ifJsonObjectAbsent() {
        Generator seedGenerator = ConversionGenerator.builder()
                .type(TYPE)
                .objectStorageFiller(OSF)
                .managerCreator(CREATOR)
                .valuedGenerator(VALUED_GENERATOR)
                .key(KEY)
                .build();
        Optional<Seed> maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isEmpty();
    }

    @Test
    void shouldCheckNextGetting() {
        Map<String, Object> expectedFields = Map.of(
                "valuedGenerator", VALUED_GENERATOR,
                "managerCreator", CREATOR,
                "entityId", ENTITY_ID,
                "key", KEY,
                "objectStorageFiller", OSF
        );

        TestJsonEntity entity = new TestJsonEntity();
        entity.setId(ENTITY_ID);

        ObjectStorage storage = new ObjectStorage();
        storage.put(ENTITY_ID, entity);

        ImmutableResult<ObjectStorage> result = ImmutableResult.<ObjectStorage>ok(storage).build();
        CREATOR.apply(CONTEXT).put(KEY, Properties.JSON_OBJECT_CREATION_RESULT, result);

        Generator seedGenerator = ConversionGenerator.builder()
                .objectStorageFiller(OSF)
                .type(TYPE)
                .managerCreator(CREATOR)
                .valuedGenerator(VALUED_GENERATOR)
                .key(KEY)
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
