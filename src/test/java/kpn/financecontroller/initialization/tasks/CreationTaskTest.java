package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.generators.valued.ValuedStringGenerator;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.tasks.testUtils.TestJsonEntity;
import kpn.financecontroller.initialization.tasks.testUtils.TestJsonObj;
import kpn.financecontroller.initialization.tasks.testUtils.TestKeys;
import kpn.financecontroller.initialization.tasks.testUtils.TestManagerCreator;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.contexts.DefaultContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class CreationTaskTest {

    private static final ValuedGenerator<String> VALUED_GENERATOR = new ValuedStringGenerator();
    private static final Function<Context, ResultContextManager> CREATOR = new TestManagerCreator();
    private static final TestKeys KEY = TestKeys.KEY;
    private static final String INVALID_SOURCE = "{\"hello\"";
    private static final String SOURCE = "{\"entities\" : {\"1\":{\"id\":1}}}";

    private static Result<TestJsonObj> expectedResultWhenNoContent;
    private static Result<TestJsonObj> expectedResultWhenJsonSyntaxException;
    private static Result<TestJsonObj> expectedResult;

    @BeforeAll
    static void beforeAll() {
        expectedResultWhenNoContent = Result.<TestJsonObj>builder()
                .success(false)
                .code(VALUED_GENERATOR.generate(TestKeys.KEY, Codes.NO_STRING_CONTENT))
                .arg(TestKeys.KEY)
                .build();

        expectedResultWhenJsonSyntaxException = Result.<TestJsonObj>builder()
                .success(false)
                .code(VALUED_GENERATOR.generate(TestKeys.KEY, Codes.JSON_SYNTAX_EXCEPTION))
                .arg(TestKeys.KEY)
                .build();

        TestJsonEntity testEntity = new TestJsonEntity();
        testEntity.setId(1L);

        TestJsonObj value = new TestJsonObj();
        value.setEntities(Map.of(1L, testEntity));

        expectedResult = Result.<TestJsonObj>builder()
                .success(true)
                .value(value)
                .arg(TestKeys.KEY)
                .build();
    }

    @Test
    void shouldCheckExecutionWithoutStringContent() {
        CreationTask task = createTask();

        Context context = new ContextBuilder().build();
        task.execute(context);

        Result<TestJsonObj> result = CREATOR.apply(context).get(KEY, Properties.JSON_OBJECT_CREATION_RESULT, TestJsonObj.class);
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

        Result<TestJsonObj> result = CREATOR.apply(context).get(KEY, Properties.JSON_OBJECT_CREATION_RESULT, TestJsonObj.class);
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

        Result<TestJsonObj> result = CREATOR.apply(context).get(KEY, Properties.JSON_OBJECT_CREATION_RESULT, TestJsonObj.class);
        assertThat(expectedResult).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isTrue();
    }

    private CreationTask createTask(){
        CreationTask task = new CreationTask();
        task.setKey(KEY);
        task.setValuedGenerator(VALUED_GENERATOR);
        task.setManagerCreator(CREATOR);
        task.setType(TestJsonObj.class);

        return task;
    }

    private static class ContextBuilder{
        private final DefaultContext context;

        public ContextBuilder() {
            this.context = new DefaultContext();
        }

        public ContextBuilder addSource(String source){
            Result<String> result = Result.<String>builder()
                    .success(true)
                    .value(source)
                    .build();
            CREATOR.apply(context).put(KEY, Properties.FILE_READING_RESULT, result);
            return this;
        }

        public Context build(){
            return context;
        }
    }
}
