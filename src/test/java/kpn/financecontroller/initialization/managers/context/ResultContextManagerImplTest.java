package kpn.financecontroller.initialization.managers.context;

import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.generators.valued.ValuedStringGenerator;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import kpn.taskexecutor.exceptions.PropertyNotFoundException;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.contexts.DefaultContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class ResultContextManagerImplTest {

    private static final String VALUE_RESULT_VALUE = "value";
    private static final int TEST_OBJECT_VALUE = 12345;
    private static final TestObject TEST_OBJECT = new TestObject(TEST_OBJECT_VALUE);

    private static final Boolean RESULT_SUCCESS = true;
    private static final String RESULT_CODE = "code";
    private static final Integer RESULT_ARG = 123;
    private static final Object[] RESULT_ARGS = new Object[]{RESULT_ARG};

    private static ValuedStringGenerator valuedStringGenerator;
    private static Result<Object> expectedResultWhenPropertyNotExist;
    private static Result<Object> expectedResultWhenExist;
    private static Result<TestObject> expectedResultWhenFailCast;
    private static Result<TestObject> expectedResultWhenTestObjectExist;

    @BeforeAll
    static void beforeAll() {
        valuedStringGenerator = new ValuedStringGenerator();

        expectedResultWhenPropertyNotExist
                = ImmutableResult.<Object>fail(ResultContextManagerImpl.Codes.PROPERTY_NOT_EXIST.getValue()).build();
        expectedResultWhenExist = ImmutableResult.<Object>ok(VALUE_RESULT_VALUE)
                .code(RESULT_CODE)
                .arg(RESULT_ARG)
                .build();
        expectedResultWhenFailCast
                = ImmutableResult.<TestObject>fail(ResultContextManagerImpl.Codes.CAST_FAIL.getValue()).build();
        expectedResultWhenTestObjectExist = ImmutableResult.<TestObject>ok(TEST_OBJECT)
                .code(RESULT_CODE)
                .arg(RESULT_ARG)
                .build();
    }

    @Test
    void shouldCheckGettingObjectResult_whenPropertyNotExist() {
        ResultContextManager testedManager = new ResultContextManagerImpl(createContextWhenPropertyNotExist(valuedStringGenerator), valuedStringGenerator);
        Result<Object> result = testedManager.get(Keys.KEY0, Properties.NOT_EXIST_PROPERTY);
        assertThat(expectedResultWhenPropertyNotExist).isEqualTo(result);
    }

    @SneakyThrows
    private static Context createContextWhenPropertyNotExist(ValuedGenerator<String> generator) {
        Context context = Mockito.mock(Context.class);
        Mockito
                .when(context.get(generator.generate(Keys.KEY0, Properties.NOT_EXIST_PROPERTY, ResultContextManagerImpl.ResultParts.SUCCESS), Boolean.class))
                .thenThrow(new PropertyNotFoundException(""));
        return context;
    }

    @Test
    void shouldCheckGetting() {
        ResultContextManager testedManager = new ResultContextManagerImpl(createContextWhenPropertyExist(valuedStringGenerator), valuedStringGenerator);
        Result<Object> result = testedManager.get(Keys.KEY0, Properties.EXIST_PROPERTY);
        assertThat(expectedResultWhenExist).isEqualTo(result);
    }

    @SneakyThrows
    private static Context createContextWhenPropertyExist(ValuedGenerator<String> generator) {
        Context context = Mockito.mock(Context.class);

        Mockito
                .when(context.get(generator.generate(Keys.KEY0, Properties.EXIST_PROPERTY, ResultContextManagerImpl.ResultParts.SUCCESS), Boolean.class))
                .thenReturn(RESULT_SUCCESS);
        Mockito
                .when(context.get(generator.generate(Keys.KEY0, Properties.EXIST_PROPERTY, ResultContextManagerImpl.ResultParts.VALUE)))
                .thenReturn(VALUE_RESULT_VALUE);
        Mockito
                .when(context.get(generator.generate(Keys.KEY0, Properties.EXIST_PROPERTY, ResultContextManagerImpl.ResultParts.CODE), String.class))
                .thenReturn(RESULT_CODE);
        Mockito
                .when(context.get(generator.generate(Keys.KEY0, Properties.EXIST_PROPERTY, ResultContextManagerImpl.ResultParts.ARGS)))
                .thenReturn(RESULT_ARGS);

        return context;
    }

    @Test
    void shouldCheckTypedGettingWhenPropertyNotExist() {
        ResultContextManager testedManager = new ResultContextManagerImpl(createContextWhenPropertyNotExist(valuedStringGenerator), valuedStringGenerator);
        Result<String> result = testedManager.get(Keys.KEY0, Properties.NOT_EXIST_PROPERTY, String.class);
        assertThat(expectedResultWhenPropertyNotExist).isEqualTo(result);
    }

    @Test
    void shouldCheckTypedGettingWhenFailCast() {
        ResultContextManager testedManager = new ResultContextManagerImpl(createContextWhenFailCast(valuedStringGenerator), valuedStringGenerator);
        Result<TestObject> result = testedManager.get(Keys.KEY0, Properties.FAIL_CAST_PROPERTY, TestObject.class);
        assertThat(expectedResultWhenFailCast).isEqualTo(result);
    }

    @SneakyThrows
    private static Context createContextWhenFailCast(ValuedGenerator<String> generator) {
        Context context = Mockito.mock(Context.class);
        Mockito
                .when(context.get(generator.generate(Keys.KEY0, Properties.FAIL_CAST_PROPERTY, ResultContextManagerImpl.ResultParts.SUCCESS), Boolean.class))
                .thenReturn(!RESULT_SUCCESS);
        Mockito
                .when(context.get(generator.generate(Keys.KEY0, Properties.FAIL_CAST_PROPERTY, ResultContextManagerImpl.ResultParts.VALUE), TestObject.class))
                .thenThrow(new ClassCastException(""));
        return context;
    }

    @Test
    void shouldCheckTypedGetting() {
        ResultContextManager testedManager = new ResultContextManagerImpl(createContextWhenObjectPropertyExist(valuedStringGenerator), valuedStringGenerator);
        Result<TestObject> result = testedManager.get(Keys.KEY0, Properties.CAST_PROPERTY, TestObject.class);
        assertThat(expectedResultWhenTestObjectExist).isEqualTo(result);
    }

    @SneakyThrows
    private static Context createContextWhenObjectPropertyExist(ValuedGenerator<String> generator) {
        Context context = Mockito.mock(Context.class);

        Mockito
                .when(context.get(generator.generate(Keys.KEY0, Properties.CAST_PROPERTY, ResultContextManagerImpl.ResultParts.SUCCESS), Boolean.class))
                .thenReturn(RESULT_SUCCESS);
        Mockito
                .when(context.get(generator.generate(Keys.KEY0, Properties.CAST_PROPERTY, ResultContextManagerImpl.ResultParts.VALUE), TestObject.class))
                .thenReturn(TEST_OBJECT);
        Mockito
                .when(context.get(generator.generate(Keys.KEY0, Properties.CAST_PROPERTY, ResultContextManagerImpl.ResultParts.CODE), String.class))
                .thenReturn(RESULT_CODE);
        Mockito
                .when(context.get(generator.generate(Keys.KEY0, Properties.CAST_PROPERTY, ResultContextManagerImpl.ResultParts.ARGS)))
                .thenReturn(RESULT_ARGS);

        return context;
    }

    @SneakyThrows
    @Test
    void shouldCheckPutting() {
        DefaultContext context = new DefaultContext();
        ResultContextManagerImpl contextManager = new ResultContextManagerImpl(context, valuedStringGenerator);

        Result<String> result = ImmutableResult.<String>builder()
                .success(RESULT_SUCCESS)
                .value(VALUE_RESULT_VALUE)
                .code(RESULT_CODE)
                .arg(RESULT_ARG)
                .build();
        contextManager.put(Keys.KEY0, Properties.PUT_PROPERTY, result);

        Object success = context.get(valuedStringGenerator.generate(Keys.KEY0, Properties.PUT_PROPERTY, ResultContextManagerImpl.ResultParts.SUCCESS));
        assertThat(RESULT_SUCCESS).isEqualTo(success);
        Object value = context.get(valuedStringGenerator.generate(Keys.KEY0, Properties.PUT_PROPERTY, ResultContextManagerImpl.ResultParts.VALUE));
        assertThat(VALUE_RESULT_VALUE).isEqualTo(value);
        Object code = context.get(valuedStringGenerator.generate(Keys.KEY0, Properties.PUT_PROPERTY, ResultContextManagerImpl.ResultParts.CODE));
        assertThat(RESULT_CODE).isEqualTo(code);
        Object args = context.get(valuedStringGenerator.generate(Keys.KEY0, Properties.PUT_PROPERTY, ResultContextManagerImpl.ResultParts.ARGS));
        assertThat(RESULT_ARGS).isEqualTo(args);
    }

    @RequiredArgsConstructor
    private enum Keys implements Valued<String> {
        KEY0("key0");

        @Getter
        private final String value;
    }

    @RequiredArgsConstructor
    private enum Properties implements Valued<String> {
        EXIST_PROPERTY("exist"),
        NOT_EXIST_PROPERTY("not.exist"),
        FAIL_CAST_PROPERTY("cast.fail"),
        CAST_PROPERTY("cast"),
        PUT_PROPERTY("put");

        @Getter
        private final String value;
    }

    @RequiredArgsConstructor
    @EqualsAndHashCode
    private static class TestObject{
        private final int x;
    }
}