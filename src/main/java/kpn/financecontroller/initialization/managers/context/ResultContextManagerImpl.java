package kpn.financecontroller.initialization.managers.context;

import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import kpn.taskexecutor.exceptions.PropertyNotFoundException;
import kpn.taskexecutor.lib.contexts.Context;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public class ResultContextManagerImpl implements ResultContextManager {
    private final Context context;
    private final ValuedGenerator<String> valuedGenerator;

    @Override
    public <T> void put(Valued<String> k, Valued<String> p, Result<T> result) {
        PropertyNames propertyNames = calculatePropertyNames(k, p);
        context.put(propertyNames.getSuccessName(), result.isSuccess());
        context.put(propertyNames.getValueName(), result.getValue());
        context.put(propertyNames.getCodeName(), result.getCode());
        context.put(propertyNames.getArgsName(), result.getArgs());
    }

    @Override
    public Result<Object> get(Valued<String> k, Valued<String> p) {
        PropertyNames propertyNames = calculatePropertyNames(k, p);
        ImmutableResult.Builder<Object> builder = ImmutableResult.<Object>builder();
        try{
            builder.success(context.get(propertyNames.getSuccessName(), Boolean.class));
        } catch (PropertyNotFoundException e){
            return createFailResult(Codes.PROPERTY_NOT_EXIST);
        }

        try{
            builder.value(context.get(propertyNames.getValueName()));
        } catch (PropertyNotFoundException ignored){}

        try{
            String code = context.get(propertyNames.getCodeName(), String.class);
            builder.code(code);
        } catch (PropertyNotFoundException ex){
            ex.printStackTrace();
        }

        try{
            Object[] args = (Object[]) context.get(propertyNames.getArgsName());
            Arrays.stream(args).forEach(builder::arg);
        } catch (PropertyNotFoundException ignored){}

        return builder.build();
    }

    @Override
    public <T> Result<T> get(Valued<String> k, Valued<String> p, Class<T> type) {
        PropertyNames propertyNames = calculatePropertyNames(k, p);
        ImmutableResult.Builder<T> builder = ImmutableResult.<T>builder();
        try{
            builder.success(context.get(propertyNames.getSuccessName(), Boolean.class));
        } catch (PropertyNotFoundException e){
            return createFailResult(Codes.PROPERTY_NOT_EXIST);
        }

        try{
            builder.value(context.get(propertyNames.getValueName(), type));
        } catch (PropertyNotFoundException ignored){}
        catch (ClassCastException e){
            return createFailResult(Codes.CAST_FAIL);
        }

        try{
            String code = context.get(propertyNames.getCodeName(), String.class);
            builder.code(code);
        } catch (PropertyNotFoundException ex){
            ex.printStackTrace();
        }

        try{
            Object[] args = (Object[]) context.get(propertyNames.getArgsName());
            Arrays.stream(args).forEach(builder::arg);
        } catch (PropertyNotFoundException ignored){}

        return builder.build();
    }

    private PropertyNames calculatePropertyNames(Valued<String> k, Valued<String> p) {
        return new PropertyNames(
                valuedGenerator.generate(k, p, ResultParts.SUCCESS),
                valuedGenerator.generate(k, p, ResultParts.VALUE),
                valuedGenerator.generate(k, p, ResultParts.CODE),
                valuedGenerator.generate(k, p, ResultParts.ARGS)
        );
    }

    private <T> Result<T> createFailResult(Codes code) {
        return ImmutableResult.<T>builder()
                .success(false)
                .code(code.getValue())
                .build();
    }

    @RequiredArgsConstructor
    public enum Codes{
        PROPERTY_NOT_EXIST("managers.context.property.notExist"),
        CAST_FAIL("managers.context.cast.fail");

        @Getter
        private final String value;
    }

    @RequiredArgsConstructor
    public enum ResultParts implements Valued<String> {
        SUCCESS("success"),
        VALUE("value"),
        CODE("code"),
        ARGS("args");

        @Getter
        private final String value;
    }

    @RequiredArgsConstructor
    @Getter
    private static class PropertyNames{
        private final String successName;
        private final String valueName;
        private final String codeName;
        private final String argsName;
    }
}
