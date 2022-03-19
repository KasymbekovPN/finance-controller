package kpn.financecontroller.initialization.managers.context;

import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.generators.valued.ValuedStringGenerator;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.exceptions.contexts.ContextPropertyNonExist;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.contexts.SimpleContext;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

public class ContextManagerImplTest {

    private static final String EXIST_VALUE = "value";
    private static final int TEST_VALUE = 123;
    private static final TestObject CAST_VALUE = new TestObject(TEST_VALUE);

    private static ValuedStringGenerator valuedStringGenerator;
    private static ContextManagerImpl testedManager;
    private static Result<Object> expectedResultWhenPropertyNotExist;
    private static Result<Object> expectedResultWhenExist;
    private static Result<TestObject> expectedResultWhenFailCast;
    private static Result<TestObject> expectedResultWhenTestObjectExist;

    @SneakyThrows
    @BeforeAll
    static void beforeAll() {
        valuedStringGenerator = new ValuedStringGenerator();
        testedManager = new ContextManagerImpl(createContext(valuedStringGenerator), valuedStringGenerator);

        expectedResultWhenPropertyNotExist = Result.<Object>builder()
                .success(false)
                .code(ContextManagerImpl.Codes.PROPERTY_NOT_EXIST.getValue())
                .build();
        expectedResultWhenExist = Result.<Object>builder()
                .success(true)
                .value(EXIST_VALUE)
                .build();
        expectedResultWhenFailCast = Result.<TestObject>builder()
                .success(false)
                .code(ContextManagerImpl.Codes.CAST_FAIL.getValue())
                .build();
        expectedResultWhenTestObjectExist = Result.<TestObject>builder()
                .success(true)
                .value(CAST_VALUE)
                .build();
    }

    @Test
    void shouldCheckGettingWhenPropertyNotExist() {
        Result<Object> result = testedManager.get(Keys.KEY0, Properties.NOT_EXIST_PROPERTY);
        assertThat(expectedResultWhenPropertyNotExist).isEqualTo(result);
    }

    @Test
    void shouldCheckGetting() {
        Result<Object> result = testedManager.get(Keys.KEY0, Properties.EXIST_PROPERTY);
        assertThat(expectedResultWhenExist).isEqualTo(result);
        assertThat(EXIST_VALUE).isEqualTo(expectedResultWhenExist.getValue());
    }

    @Test
    void shouldCheckTypedGettingWhenPropertyNotExist() {
        Result<String> result = testedManager.get(Keys.KEY0, Properties.NOT_EXIST_PROPERTY, String.class);
        assertThat(expectedResultWhenPropertyNotExist).isEqualTo(result);
    }

    @Test
    void shouldCheckTypedGettingWhenFailCast() {
        Result<TestObject> result = testedManager.get(Keys.KEY0, Properties.FAIL_CAST_PROPERTY, TestObject.class);
        assertThat(expectedResultWhenFailCast).isEqualTo(result);
    }

    @Test
    void shouldCheckTypedGetting() {
        Result<TestObject> result = testedManager.get(Keys.KEY0, Properties.CAST_PROPERTY, TestObject.class);
        assertThat(expectedResultWhenTestObjectExist).isEqualTo(result);
    }

    @SneakyThrows
    @Test
    void shouldCheckPutting() {
        SimpleContext context = new SimpleContext();
        ContextManagerImpl manager = new ContextManagerImpl(context, valuedStringGenerator);
        String expectedValue = "value";
        manager.put(Keys.KEY0, Properties.EXIST_PROPERTY, expectedValue);
        String value = context.get(valuedStringGenerator.generate(Keys.KEY0, Properties.EXIST_PROPERTY), String.class);
        assertThat(expectedValue).isEqualTo(value);
    }

    private static Context createContext(ValuedGenerator<String> generator) throws ContextPropertyNonExist {
        Context context = Mockito.mock(Context.class);

        Mockito
                .when(context.get(generator.generate(Keys.KEY0, Properties.NOT_EXIST_PROPERTY)))
                .thenThrow(new ContextPropertyNonExist(""));

        Mockito
                .when(context.get(generator.generate(Keys.KEY0, Properties.NOT_EXIST_PROPERTY), String.class))
                .thenThrow(new ContextPropertyNonExist(""));

        Mockito
                .when(context.get(generator.generate(Keys.KEY0, Properties.EXIST_PROPERTY)))
                .thenReturn(EXIST_VALUE);

        Mockito
                .when(context.get(generator.generate(Keys.KEY0, Properties.FAIL_CAST_PROPERTY), TestObject.class))
                .thenThrow(new ClassCastException());

        Mockito
                .when(context.get(generator.generate(Keys.KEY0, Properties.CAST_PROPERTY), TestObject.class))
                .thenReturn(CAST_VALUE);

        return context;
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
        CAST_PROPERTY("cast");

        @Getter
        private final String value;
    }

    @RequiredArgsConstructor
    @EqualsAndHashCode
    private static class TestObject{
        private final int x;
    }
}
