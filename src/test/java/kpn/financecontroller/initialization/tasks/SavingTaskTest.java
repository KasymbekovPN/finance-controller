package kpn.financecontroller.initialization.tasks;

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

import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class SavingTaskTest {

    private static final Valued<String> KEY = TestKeys.KEY;
    private static final ValuedGenerator<String> VALUED_GENERATOR = new ValuedStringGenerator();
    private static final Function<Context, ResultContextManager> CREATOR = new TestManagerCreator();
    private static final Long ENTITY_ID = 1L;
    private static final Object ENTITY = new Object();
    private static final SavingTask.Strategy SUCCESS_STRATEGY = value -> Optional.empty();
    private static final SavingTask.Strategy FAIL_STRATEGY = value -> Optional.of(Codes.FAIL_SAVING_ATTEMPT);

    private static Result<Void> expectedResultIfConversionResultNotExist;
    private static Result<Void> expectedResultIfEntityNotExist;
    private static Result<Void> expectedResultIfFailSaving;
    private static Result<Void> expectedResult;

    @BeforeAll
    static void beforeAll() {
        expectedResultIfConversionResultNotExist
                = ImmutableResult.<Void>fail(VALUED_GENERATOR.generate(KEY, Codes.CONVERSION_RESULT_NOT_EXIST_ON_SAVING)).arg(KEY).build();
        expectedResultIfEntityNotExist
                = ImmutableResult.<Void>fail(VALUED_GENERATOR.generate(KEY, Codes.ENTITY_NOT_EXIST_ON_SAVING)).arg(KEY).build();
        expectedResultIfFailSaving
                = ImmutableResult.<Void>fail(VALUED_GENERATOR.generate(KEY, Codes.FAIL_SAVING_ATTEMPT)).arg(KEY).build();
        expectedResult = ImmutableResult.<Void>builder()
                .success(true)
                .arg(KEY)
                .build();
    }

    @Test
    void shouldCheckExecution_ifConversionResultNotExist() {
        Context context = new ContextBuilder().build();

        SavingTask task = createTask(SUCCESS_STRATEGY);
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

        SavingTask task = createTask(SUCCESS_STRATEGY);
        task.execute(context);

        Result<Void> result = CREATOR.apply(context).get(TestKeys.KEY, Properties.SAVING_RESULT, Void.class);
        assertThat(expectedResultIfEntityNotExist).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isFalse();
    }

    @Test
    void shouldCheckExecution_ifSavingFail() {
        Context context = new ContextBuilder()
                .addStorage()
                .addEntity(ENTITY_ID, ENTITY)
                .build();

        SavingTask task = createTask(FAIL_STRATEGY);
        task.execute(context);

        Result<Void> result = CREATOR.apply(context).get(TestKeys.KEY, Properties.SAVING_RESULT, Void.class);
        assertThat(expectedResultIfFailSaving).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isFalse();
    }

    @Test
    void shouldCheckExecution() {
        Context context = new ContextBuilder()
                .addStorage()
                .addEntity(ENTITY_ID, ENTITY)
                .build();

        SavingTask task = createTask(SUCCESS_STRATEGY);
        task.execute(context);

        Result<Void> result = CREATOR.apply(context).get(TestKeys.KEY, Properties.SAVING_RESULT, Void.class);
        assertThat(expectedResult).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isTrue();
    }

    private SavingTask createTask(SavingTask.Strategy strategy){
        SavingTask task = new SavingTask();
        task.setKey(KEY);
        task.setValuedGenerator(VALUED_GENERATOR);
        task.setManagerCreator(CREATOR);
        task.setEntityId(ENTITY_ID);
        task.setStrategy(strategy);

        return task;
    }

    private static class ContextBuilder{
        private final DefaultContext context;

        private ObjectStorage storage;

        public ContextBuilder() {
            this.context = new DefaultContext();
        }

        public ContextBuilder addStorage(){
            this.storage = new ObjectStorage();
            return this;
        }

        public ContextBuilder addEntity(Long id, Object value){
            if (storage != null){
                storage.put(id, value);
            }
            return this;
        }

        public Context build(){
            if (storage != null){
                Result<ObjectStorage> tagStorageResult = ImmutableResult.<ObjectStorage>ok(storage).build();
                CREATOR.apply(context).put(TestKeys.KEY, Properties.JSON_TO_DB_CONVERSION_RESULT, tagStorageResult);
            }
            return context;
        }
    }
}