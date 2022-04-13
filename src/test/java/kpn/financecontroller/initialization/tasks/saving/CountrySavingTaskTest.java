package kpn.financecontroller.initialization.tasks.saving;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.data.services.savers.Saver;
import kpn.financecontroller.initialization.generators.valued.*;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.storages.CountryStorage;
import kpn.financecontroller.initialization.tasks.saving.CountrySavingTask;
import kpn.financecontroller.initialization.tasks.testUtils.TestKeys;
import kpn.financecontroller.initialization.tasks.testUtils.TestManagerCreator;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.contexts.DefaultContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class CountrySavingTaskTest {

    private static final Valued<String> KEY = TestKeys.KEY;
    private static final ValuedGenerator<String> VALUED_GENERATOR = new ValuedStringGenerator();
    private static final Function<Context, ResultContextManager> CREATOR = new TestManagerCreator();

    private static final Long ENTITY_ID = 1L;

    private static Result<Void> expectedResultIfConversionResultNotExist;
    private static Result<Void> expectedResultIfEntityNotExist;
    private static Result<Void> expectedResultIfFailSaving;
    private static Result<Void> expectedResult;

    @BeforeAll
    static void beforeAll() {
        expectedResultIfConversionResultNotExist = ImmutableResult.<Void>builder()
                .success(false)
                .code(VALUED_GENERATOR.generate(KEY, Codes.CONVERSION_RESULT_NOT_EXIST_ON_SAVING))
                .arg(KEY)
                .build();
        expectedResultIfEntityNotExist = ImmutableResult.<Void>builder()
                .success(false)
                .code(VALUED_GENERATOR.generate(KEY, Codes.ENTITY_NOT_EXIST_ON_SAVING))
                .arg(KEY)
                .build();
        expectedResultIfFailSaving = ImmutableResult.<Void>builder()
                .success(false)
                .code(VALUED_GENERATOR.generate(KEY, Codes.FAIL_SAVING_ATTEMPT))
                .arg(KEY)
                .build();
        expectedResult = ImmutableResult.<Void>builder()
                .success(true)
                .arg(KEY)
                .build();
    }

    @Test
    void shouldCheckExecution_ifConversionResultNotExist() {
        Context context = new ContextBuilder().build();

        CountrySavingTask task = createTask(createDTOService(createFailSaver()));
        task.execute(context);

        Result<Void> result = CREATOR.apply(context).get(TestKeys.KEY, Properties.SAVING_RESULT, Void.class);
        assertThat(expectedResultIfConversionResultNotExist).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isFalse();
    }

    @Test
    void shouldCheckExecution_ifEntityNotPointed() {
        Context context = new ContextBuilder()
                .addStorage()
                .build();

        CountrySavingTask task = createTask(createDTOService(createFailSaver()));
        task.execute(context);

        Result<Void> result = CREATOR.apply(context).get(TestKeys.KEY, Properties.SAVING_RESULT, Void.class);
        assertThat(expectedResultIfEntityNotExist).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isFalse();
    }

    @Test
    void shouldCheckExecution_ifSavingFail() {
        Context context = new ContextBuilder()
                .addStorage()
                .addEntity(ENTITY_ID, "")
                .build();

        CountrySavingTask task = createTask(createDTOService(createFailSaver()));
        task.execute(context);

        Result<Void> result = CREATOR.apply(context).get(TestKeys.KEY, Properties.SAVING_RESULT, Void.class);
        assertThat(expectedResultIfFailSaving).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isFalse();
    }

    @Test
    void shouldCheckExecution() {
        Context context = new ContextBuilder()
                .addStorage()
                .addEntity(ENTITY_ID, "")
                .build();

        CountrySavingTask task = createTask(createDTOService(createSaver()));
        task.execute(context);

        Result<Void> result = CREATOR.apply(context).get(TestKeys.KEY, Properties.SAVING_RESULT, Void.class);
        assertThat(expectedResult).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isTrue();
    }

    private CountrySavingTask createTask(TestDTOService service){
        CountrySavingTask task = new CountrySavingTask();
        task.setKey(KEY);
        task.setValuedGenerator(VALUED_GENERATOR);
        task.setManagerCreator(CREATOR);
        task.setDtoService(service);
        task.setEntityId(ENTITY_ID);

        return task;
    }

    private TestSaver createSaver() {
        TestSaver saver = Mockito.mock(TestSaver.class);
        Country country = new Country();
        country.setId(ENTITY_ID);
        Mockito
                .when(saver.save(Mockito.any(CountryEntity.class)))
                .thenReturn(ImmutableResult.<Country>ok(country).build());
        return saver;
    }

    private TestSaver createFailSaver() {
        TestSaver saver = Mockito.mock(TestSaver.class);
        Mockito
                .when(saver.save(Mockito.any(CountryEntity.class)))
                .thenReturn(ImmutableResult.<Country>builder().success(false).build());
        return saver;
    }

    private TestDTOService createDTOService(TestSaver saver) {
        TestDTOService service = Mockito.mock(TestDTOService.class);
        Mockito
                .when(service.saver())
                .thenReturn(saver);
        return service;
    }

    public abstract static class TestSaver implements Saver<Country, CountryEntity, Long> {}
    public abstract static class TestDTOService implements DTOService<Country, CountryEntity, Long> {}

    private static class ContextBuilder{
        private final DefaultContext context;

        private CountryStorage storage;

        public ContextBuilder() {
            this.context = new DefaultContext();
        }

        public ContextBuilder addStorage(){
            this.storage = new CountryStorage();
            return this;
        }

        public ContextBuilder addEntity(Long id, String name){
            if (storage != null){
                CountryEntity entity = new CountryEntity();
                entity.setId(id);
                entity.setName(name);
                storage.put(id, entity);
            }
            return this;
        }

        public Context build(){
            if (storage != null){
                Result<CountryStorage> tagStorageResult = ImmutableResult.<CountryStorage>ok(storage).build();
                CREATOR.apply(context).put(TestKeys.KEY, Properties.JSON_TO_DB_CONVERSION_RESULT, tagStorageResult);
            }
            return context;
        }
    }
}