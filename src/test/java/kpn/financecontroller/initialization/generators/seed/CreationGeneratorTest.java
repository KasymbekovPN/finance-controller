package kpn.financecontroller.initialization.generators.seed;

import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.generators.valued.ValuedStringGenerator;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.storage.ObjectStorage;
import kpn.financecontroller.initialization.tasks.CreationTask;
import kpn.financecontroller.initialization.tasks.testUtils.TestKeys;
import kpn.financecontroller.initialization.tasks.testUtils.TestManagerCreator;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.contexts.DefaultContext;
import kpn.taskexecutor.lib.seed.Seed;
import kpn.taskexecutor.lib.seed.generator.Generator;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class CreationGeneratorTest {
    private static final Context CONTEXT = new DefaultContext();
    private static final Function<Context, ResultContextManager> CREATOR = new TestManagerCreator();
    private static final ValuedGenerator<String> VALUED_GENERATOR = new ValuedStringGenerator();
    private static final Valued<String> KEY = TestKeys.KEY;
    private static final CreationTask.ObjectStorageCreator OSC = (str) -> {
        return new ObjectStorage();
    };

    @Test
    void shouldCheckNextGetting_ifManagerCreatorNull() {
        Generator seedGenerator = CreationGenerator.builder().build();
        Optional<Seed> maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isEmpty();
    }

    @Test
    void shouldCheckNextGetting_ifValuedGeneratorNull() {
        Generator seedGenerator = CreationGenerator.builder()
                .managerCreator(CREATOR)
                .build();

        Optional<Seed> maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isEmpty();
    }

    @Test
    void shouldCheckNextGetting() {
        Map<String, Object> expectedFields = Map.of(
                "valuedGenerator", VALUED_GENERATOR,
                "managerCreator", CREATOR,
                "key", KEY,
                "objectStorageCreator", OSC
        );

        Generator seedGenerator = CreationGenerator.builder()
                .item(KEY, OSC)
                .managerCreator(CREATOR)
                .valuedGenerator(VALUED_GENERATOR)
                .key(KEY)
                .build();

        Optional<Seed> maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isPresent();
        Seed seed = maybeSeed.get();
        assertThat(seed.getType()).isEqualTo(CreationTask.class);
        assertThat(seed.getFields()).isEqualTo(expectedFields);

        maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isEmpty();
    }

    // TODO: 17.04.2022 del
//    public static class TestJsonObj extends LongKeyJsonObj<String>{}
}
