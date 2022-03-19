package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.generators.valued.ValuedStringGenerator;
import kpn.financecontroller.initialization.tasks.testUtils.TestEntity;
import kpn.financecontroller.initialization.tasks.testUtils.TestJsonObj;
import kpn.financecontroller.initialization.tasks.testUtils.TestKeys;
import kpn.financecontroller.initialization.tasks.testUtils.TestManagerCreator;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.lib.contexts.SimpleContext;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonObjCreationTaskTest {

    private static final ValuedGenerator<String> VALUED_GENERATOR = new ValuedStringGenerator();
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

        TestEntity testEntity = new TestEntity();
        testEntity.setId(1L);

        TestJsonObj value = new TestJsonObj();
        value.setEntities(Map.of(1L, testEntity));

        expectedResult = Result.<TestJsonObj>builder()
                .success(true)
                .value(value)
                .arg(TestKeys.KEY)
                .build();
    }

    @SneakyThrows
    @Test
    void shouldCheckExecutionWithoutStringContent() {
        JsonObjCreationTask task
                = new JsonObjCreationTask(TestKeys.KEY, new ValuedStringGenerator(), new TestManagerCreator(), TestJsonObj.class);
        SimpleContext context = new SimpleContext();
        task.execute(context);

        Result<TestJsonObj> result = (Result<TestJsonObj>) context.get(VALUED_GENERATOR.generate(TestKeys.KEY, Properties.JSON_OBJECT_CREATION_RESULT));
        assertThat(expectedResultWhenNoContent).isEqualTo(result);
    }

    @SneakyThrows
    @Test
    void shouldCheckExecutionWhenJsonSyntaxException() {
        JsonObjCreationTask task
                = new JsonObjCreationTask(TestKeys.KEY, new ValuedStringGenerator(), new TestManagerCreator(), TestJsonObj.class);
        SimpleContext context = new SimpleContext();
        context.put(VALUED_GENERATOR.generate(TestKeys.KEY, Properties.FILE_READING_RESULT), Result.<String>builder().value(INVALID_SOURCE).build());
        task.execute(context);

        Result<TestJsonObj> result = (Result<TestJsonObj>) context.get(VALUED_GENERATOR.generate(TestKeys.KEY, Properties.JSON_OBJECT_CREATION_RESULT));
        assertThat(expectedResultWhenJsonSyntaxException).isEqualTo(result);
    }

    @SneakyThrows
    @Test
    void shouldCheckExecution() {
        JsonObjCreationTask task
                = new JsonObjCreationTask(TestKeys.KEY, new ValuedStringGenerator(), new TestManagerCreator(), TestJsonObj.class);
        SimpleContext context = new SimpleContext();
        context.put(VALUED_GENERATOR.generate(TestKeys.KEY, Properties.FILE_READING_RESULT), Result.<String>builder().value(SOURCE).build());
        task.execute(context);

        Result<TestJsonObj> result = (Result<TestJsonObj>) context.get(VALUED_GENERATOR.generate(TestKeys.KEY, Properties.JSON_OBJECT_CREATION_RESULT));
        assertThat(expectedResult).isEqualTo(result);
    }
}
