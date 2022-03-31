package kpn.financecontroller.initialization.generators.seed;

import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.generators.valued.ValuedStringGenerator;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.storages.TagStorage;
import kpn.financecontroller.initialization.tasks.testUtils.TestKeys;
import kpn.financecontroller.initialization.tasks.testUtils.TestManagerCreator;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.contexts.SimpleContext;
import kpn.taskexecutor.lib.generators.Generator;
import kpn.taskexecutor.lib.seeds.Seed;
import kpn.taskexecutor.lib.tasks.Task;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class SavingGeneratorTest {

    private static final Context CONTEXT = new SimpleContext();
    private static final Function<Context, ResultContextManager> CREATOR = new TestManagerCreator();
    private static final ValuedGenerator<String> VALUED_GENERATOR = new ValuedStringGenerator();
    private static final Valued<String> KEY = TestKeys.KEY;
    private static final Class<? extends Task> TYPE = Task.class;
    private static final Long ENTITY_ID = 1L;
    private static final Class<? extends Map<Long, ?>> STORAGE_TYPE = TagStorage.class;

    @Test
    void shouldCheckNextGetting_ifManagerCreatorNull() {
        Generator seedGenerator = SavingGenerator.builder().build();
        Optional<Seed> maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isEmpty();
    }

    @Test
    void shouldCheckNextGetting_ifValuedGeneratorNull() {
        Generator seedGenerator = SavingGenerator.builder()
                .managerCreator(CREATOR)
                .build();
        Optional<Seed> maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isEmpty();
    }

    @Test
    void shouldCheckNextGetting_ifKeyNull() {
        Generator seedGenerator = SavingGenerator.builder()
                .managerCreator(CREATOR)
                .valuedGenerator(VALUED_GENERATOR)
                .build();
        Optional<Seed> maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isEmpty();
    }

    @Test
    void shouldCheckNextGetting_ifTypeNull() {
        Generator seedGenerator = SavingGenerator.builder()
                .managerCreator(CREATOR)
                .valuedGenerator(VALUED_GENERATOR)
                .key(KEY)
                .build();
        Optional<Seed> maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isEmpty();
    }

    @Test
    void shouldCheckNextGetting_ifDTOServiceNull() {
        Generator seedGenerator = SavingGenerator.builder()
                .type(TYPE)
                .managerCreator(CREATOR)
                .valuedGenerator(VALUED_GENERATOR)
                .key(KEY)
                .build();
        Optional<Seed> maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isEmpty();
    }

    @Test
    void shouldCheckNextGetting_ifStorageTypeNull() {
        Generator seedGenerator = SavingGenerator.builder()
                .type(TYPE)
                .dtoService(createDTOService())
                .managerCreator(CREATOR)
                .valuedGenerator(VALUED_GENERATOR)
                .key(KEY)
                .build();
        Optional<Seed> maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isEmpty();
    }

    @Test
    void shouldCheckNextGetting_ifConversionResultAbsent() {
        Generator seedGenerator = SavingGenerator.builder()
                .type(TYPE)
                .dtoService(createDTOService())
                .storageType(STORAGE_TYPE)
                .managerCreator(CREATOR)
                .valuedGenerator(VALUED_GENERATOR)
                .key(KEY)
                .build();
        Optional<Seed> maybeSeed = seedGenerator.getNextIfExist(CONTEXT);
        assertThat(maybeSeed).isEmpty();
    }

    @Test
    void shouldCheckNextGetting() {
        DTOService<?, ?, Long> dtoService = createDTOService();
        Map<String, Object> expectedFields = Map.of(
                "valuedGenerator", VALUED_GENERATOR,
                "managerCreator", CREATOR,
                "key", KEY,
                "dtoService", dtoService,
                "entityId", ENTITY_ID
        );

        TagEntity tagEntity = new TagEntity();
        tagEntity.setId(ENTITY_ID);
        tagEntity.setName("name");

        TagStorage tagStorage = new TagStorage();
        tagStorage.put(ENTITY_ID, tagEntity);

        Result<TagStorage> result = Result.<TagStorage>builder()
                .success(true)
                .value(tagStorage)
                .build();
        CREATOR.apply(CONTEXT).put(KEY, Properties.JSON_TO_DB_CONVERSION_RESULT, result);

        Generator seedGenerator = SavingGenerator.builder()
                .type(TYPE)
                .dtoService(dtoService)
                .storageType(STORAGE_TYPE)
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

    private DTOService<?, ?, Long> createDTOService() {
        return Mockito.mock(CleanupGeneratorTest.TestDTOService.class);
    }

    public abstract static class TestDTOService implements DTOService<String, String, Long> {}
}
