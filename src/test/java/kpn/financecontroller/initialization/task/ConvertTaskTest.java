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

    private static final String CODE__CONTEXT_NO_COLLECTOR_TYPE = "task.code.noCollectorType";
    private static final String CODE__CONTEXT_NO_SOURCE = "task.code.noFileContent";
    private static final String CODE__CONVERSION_FAIL = "task.code.conversionFail";

    private static final String PROPERTY__FILE_CONTENT = "file.reading.result";
    private static final String PROPERTY__CONVERSION_RESULT = "task.property.conversionResult";
    private static final String PROPERTY__CLASS_TYPE = "task.property.classType";

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
                .code(CODE__CONTEXT_NO_COLLECTOR_TYPE)
                .arg(KEY)
                .build();
        expectedResultWhenFileContentNotExists = Result.<TestCollector>builder()
                .success(false)
                .code(CODE__CONTEXT_NO_SOURCE)
                .arg(KEY)
                .build();
        expectedResultWhenConversionFail = Result.<TestCollector>builder()
                .success(false)
                .code(CODE__CONVERSION_FAIL)
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
        ConvertTask task = new ConvertTask(KEY);
        task.execute(context);
        Optional<Object> maybeResult = context.get(KEY, PROPERTY__CONVERSION_RESULT);
        assertThat(maybeResult).isPresent();
        Result<TestCollector> result = (Result<TestCollector>) maybeResult.get();
        assertThat(expectedResultWhenCollectorTypeNotExists).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isFalse();
    }

    @Test
    void shouldCheckExecution_withoutFileContentInContext() {
        ContextImpl context = new ContextImpl();
        context.put(KEY, PROPERTY__CLASS_TYPE, TestCollector.class);

        ConvertTask task = new ConvertTask(KEY);
        task.execute(context);
        Optional<Object> maybeResult = context.get(KEY, PROPERTY__CONVERSION_RESULT);
        assertThat(maybeResult).isPresent();
        Result<TestCollector> result = (Result<TestCollector>) maybeResult.get();
        assertThat(expectedResultWhenFileContentNotExists).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isFalse();
    }

    @Test
    void shouldCheckExecution_withFailConversion() {
        ContextImpl context = new ContextImpl();
        context.put(KEY, PROPERTY__CLASS_TYPE, TestCollector.class);
        context.put(KEY, PROPERTY__FILE_CONTENT, INVALID_SOURCE);

        ConvertTask task = new ConvertTask(KEY);
        task.execute(context);
        Optional<Object> maybeResult = context.get(KEY, PROPERTY__CONVERSION_RESULT);
        assertThat(maybeResult).isPresent();
        Result<TestCollector> result = (Result<TestCollector>) maybeResult.get();
        assertThat(expectedResultWhenConversionFail).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isFalse();
    }

    @Test
    void shouldCheckExecution() {
        ContextImpl context = new ContextImpl();
        context.put(KEY, PROPERTY__CLASS_TYPE, TestCollector.class);
        context.put(KEY, PROPERTY__FILE_CONTENT, SOURCE);

        ConvertTask task = new ConvertTask(KEY);
        task.execute(context);
        Optional<Object> maybeResult = context.get(KEY, PROPERTY__CONVERSION_RESULT);
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
