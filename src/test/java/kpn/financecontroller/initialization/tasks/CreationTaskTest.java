package kpn.financecontroller.initialization.tasks;

import com.google.gson.Gson;
import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.generators.valued.ValuedStringGenerator;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.storage.ObjectStorage;
import kpn.financecontroller.initialization.tasks.testUtils.TestJsonEntity;
import kpn.financecontroller.initialization.tasks.testUtils.TestJsonObj;
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

public class CreationTaskTest {

    private static final ValuedGenerator<String> VALUED_GENERATOR = new ValuedStringGenerator();
    private static final Function<Context, ResultContextManager> CREATOR = new TestManagerCreator();
    private static final TestKeys KEY = TestKeys.KEY;
    private static final String INVALID_SOURCE = "{\"hello\"";
    private static final String SOURCE = "{\"entities\" : {\"1\":{\"id\":1}}}";

    private static Result<ObjectStorage> expectedResultWhenNoContent;
    private static Result<ObjectStorage> expectedResultWhenJsonSyntaxException;
    private static Result<ObjectStorage> expectedResult;

    @BeforeAll
    static void beforeAll() {
        expectedResultWhenNoContent = ImmutableResult.<ObjectStorage>fail(VALUED_GENERATOR.generate(TestKeys.KEY, Codes.NO_STRING_CONTENT))
                .arg(TestKeys.KEY)
                .build();

        expectedResultWhenJsonSyntaxException = ImmutableResult.<ObjectStorage>fail(VALUED_GENERATOR.generate(TestKeys.KEY, Codes.JSON_SYNTAX_EXCEPTION))
                .arg(TestKeys.KEY)
                .build();

        TestJsonEntity testEntity = new TestJsonEntity();
        testEntity.setId(1L);

        ObjectStorage storage = new ObjectStorage();
        storage.put(1L, testEntity);

        expectedResult = ImmutableResult.<ObjectStorage>ok(storage)
                .arg(TestKeys.KEY)
                .build();
    }

    @Test
    void shouldCheckExecutionWithoutStringContent() {
        CreationTask task = createTask();

        Context context = new ContextBuilder().build();
        task.execute(context);

        Result<ObjectStorage> result = CREATOR.apply(context).get(KEY, Properties.JSON_OBJECT_CREATION_RESULT, ObjectStorage.class);
        assertThat(expectedResultWhenNoContent).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isFalse();
    }

    @Test
    void shouldCheckExecutionWhenJsonSyntaxException() {
        CreationTask task = createTask();

        Context context = new ContextBuilder()
                .addSource(INVALID_SOURCE)
                .build();
        task.execute(context);

        Result<ObjectStorage> result = CREATOR.apply(context).get(KEY, Properties.JSON_OBJECT_CREATION_RESULT, ObjectStorage.class);
        assertThat(expectedResultWhenJsonSyntaxException).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isFalse();
    }

    @Test
    void shouldCheckExecution() {
        CreationTask task = createTask();

        Context context = new ContextBuilder()
                .addSource(SOURCE)
                .build();
        task.execute(context);

        Result<ObjectStorage> result = CREATOR.apply(context).get(KEY, Properties.JSON_OBJECT_CREATION_RESULT, ObjectStorage.class);
        assertThat(expectedResult).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isTrue();
    }

    private CreationTask createTask(){
        CreationTask task = new CreationTask();
        task.setKey(KEY);
        task.setValuedGenerator(VALUED_GENERATOR);
        task.setManagerCreator(CREATOR);

        CreationTask.ObjectStorageCreator osc = (str) -> {
            TestJsonObj testJsonObj = new Gson().fromJson(str, TestJsonObj.class);
            ObjectStorage storage = new ObjectStorage();
            storage.putAll(testJsonObj.getEntities());

            return storage;
        };
        task.setObjectStorageCreator(osc);

        return task;
    }

    private static class ContextBuilder{
        private final DefaultContext context;

        public ContextBuilder() {
            this.context = new DefaultContext();
        }

        public ContextBuilder addSource(String source){
            Result<String> result = ImmutableResult.<String>ok(source).build();
            CREATOR.apply(context).put(KEY, Properties.FILE_READING_RESULT, result);
            return this;
        }

        public Context build(){
            return context;
        }
    }
}
