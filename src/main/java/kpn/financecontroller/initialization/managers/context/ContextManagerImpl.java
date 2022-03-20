package kpn.financecontroller.initialization.managers.context;

import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.exceptions.contexts.ContextPropertyNonExist;
import kpn.taskexecutor.lib.contexts.Context;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// TODO: 20.03.2022 del
@RequiredArgsConstructor
public class ContextManagerImpl implements ContextManager {
    private final Context context;
    private final ValuedGenerator<String> valuedGenerator;

    @Override
    public void put(Valued<String> key, Valued<String> property, Object value) {
        String contextProperty = calculateContextProperty(key, property);
        context.put(contextProperty, value);
    }

    @Override
    public Result<Object> get(Valued<String> key, Valued<String> property) {
        String contextProperty = calculateContextProperty(key, property);
        try {
            Object result = context.get(contextProperty);
            return getResult(result);
        } catch (ContextPropertyNonExist e) {
            return createFailResult(Codes.PROPERTY_NOT_EXIST);
        }
    }

    @Override
    public <T> Result<T> get(Valued<String> key, Valued<String> property, Class<T> type) {
        String contextProperty = calculateContextProperty(key, property);
        try {
            T result = context.get(contextProperty, type);
            return getResult(result);
        } catch (ContextPropertyNonExist e) {
            return createFailResult(Codes.PROPERTY_NOT_EXIST);
        } catch (ClassCastException e){
            return createFailResult(Codes.CAST_FAIL);
        }
    }

    private <T> Result<T> getResult(T result) {
        return Result.<T>builder()
                .success(true)
                .value(result)
                .build();
    }

    private <T> Result<T> createFailResult(Codes code) {
        return Result.<T>builder()
                .success(false)
                .code(code.getValue())
                .build();
    }

    private String calculateContextProperty(Valued<String> key, Valued<String> property) {
        return valuedGenerator.generate(key, property);
    }

    @RequiredArgsConstructor
    public enum Codes{
        PROPERTY_NOT_EXIST("managers.context.property.notExist"),
        CAST_FAIL("managers.context.cast.fail");

        @Getter
        private final String value;
    }
}
