package kpn.financecontroller.initialization.task;

import kpn.financecontroller.initialization.collector.LongKeyInitialEntityCollector;
import kpn.financecontroller.initialization.context.ContextImpl;
import kpn.financecontroller.result.Result;
import lombok.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ConvertTaskTest {

    private static final String KEY = "some.key";

    private static final String INVALID_SOURCE = "{\"hello\"";
    private static final String SOURCE = "{\"entities\" : {\"1\":{\"x\":1}}}";

    private static Result<TestCollector> expectedResultWhenCollectorTypeNotExists;
    private static Result<TestCollector> expectedResultWhenFileContentNotExists;
    private static Result<TestCollector> expectedResultWhenConversionFail;
    private static Result<TestCollector> expectedResult;

    @BeforeAll
    static void beforeAll() {
        expectedResultWhenCollectorTypeNotExists = Result.<TestCollector>builder()
                .success(false)
                .code(ConvertTask.Codes.NO_COLLECTOR_TYPE.getValue())
                .arg(KEY)
                .build();
        expectedResultWhenFileContentNotExists = Result.<TestCollector>builder()
                .success(false)
                .code(ConvertTask.Codes.NO_SOURCE.getValue())
                .arg(KEY)
                .build();
        expectedResultWhenConversionFail = Result.<TestCollector>builder()
                .success(false)
                .code(ConvertTask.Codes.FAIL_CONVERSION.getValue())
                .arg(KEY)
                .build();

        TestCollector testCollector = new TestCollector();
        testCollector.setEntities(Map.of(1L, new TestEntity(1)));
        expectedResult = Result.<TestCollector>builder()
                .success(true)
                .value(testCollector)
                .arg(KEY)
                .build();
    }

    @Test
    void shouldCheckExecution_withoutClassTypeInContext() {
        ContextImpl context = new ContextImpl();
        ConvertTask task = new ConvertTask(KEY, FileReadingTask.Properties.PATH.getValue());
        task.execute(context);
        Optional<Object> maybeResult = context.get(KEY, ConvertTask.Properties.RESULT.getValue());
        assertThat(maybeResult).isPresent();
        Result<TestCollector> result = (Result<TestCollector>) maybeResult.get();
        assertThat(expectedResultWhenCollectorTypeNotExists).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isFalse();
    }

    @Test
    void shouldCheckExecution_withoutFileContentInContext() {
        ContextImpl context = new ContextImpl();
        context.put(KEY, ConvertTask.Properties.COLLECTOR_TYPE.getValue(), TestCollector.class);

        ConvertTask task = new ConvertTask(KEY, FileReadingTask.Properties.PATH.getValue());
        task.execute(context);
        Optional<Object> maybeResult = context.get(KEY, ConvertTask.Properties.RESULT.getValue());
        assertThat(maybeResult).isPresent();
        Result<TestCollector> result = (Result<TestCollector>) maybeResult.get();
        assertThat(expectedResultWhenFileContentNotExists).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isFalse();
    }

    @Test
    void shouldCheckExecution_withFailConversion() {
        ContextImpl context = new ContextImpl();
        context.put(KEY, ConvertTask.Properties.COLLECTOR_TYPE.getValue(), TestCollector.class);
        context.put(KEY, FileReadingTask.Properties.RESULT.getValue(), INVALID_SOURCE);

        ConvertTask task = new ConvertTask(KEY, FileReadingTask.Properties.RESULT.getValue());
        task.execute(context);
        Optional<Object> maybeResult = context.get(KEY, ConvertTask.Properties.RESULT.getValue());
        assertThat(maybeResult).isPresent();
        Result<TestCollector> result = (Result<TestCollector>) maybeResult.get();
        assertThat(expectedResultWhenConversionFail).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isFalse();
    }

    @Test
    void shouldCheckExecution() {
        ContextImpl context = new ContextImpl();
        context.put(KEY, ConvertTask.Properties.COLLECTOR_TYPE.getValue(), TestCollector.class);
        context.put(KEY, FileReadingTask.Properties.RESULT.getValue(), SOURCE);

        ConvertTask task = new ConvertTask(KEY, FileReadingTask.Properties.RESULT.getValue());
        task.execute(context);
        Optional<Object> maybeResult = context.get(KEY, ConvertTask.Properties.RESULT.getValue());
        assertThat(maybeResult).isPresent();
        Result<TestCollector> result = (Result<TestCollector>) maybeResult.get();
        assertThat(expectedResult).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isTrue();
    }

    @EqualsAndHashCode(callSuper = false)
    private static class TestCollector extends LongKeyInitialEntityCollector<TestEntity>{}

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class TestEntity{
        private int x;
    }
}
