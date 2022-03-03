package kpn.financecontroller.initialization.task;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import kpn.financecontroller.initialization.collector.LongKeyInitialEntityCollector;
import kpn.financecontroller.initialization.context.Context;
import kpn.financecontroller.result.Result;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ConvertTask implements Task{

    @Getter
    private final String key;
    private final String sourceProperty;
    @Getter
    private boolean continuationPossible;

    private Class<?> classType;
    private String source;

    @Override
    public void execute(Context context) {
        continuationPossible = false;
        if (checkClassTypeOrSetResult(context) && checkFileContentOrSetResult(context)){
            try{
                Result<LongKeyInitialEntityCollector<?>> result = Result.<LongKeyInitialEntityCollector<?>>builder()
                        .success(true)
                        .value((LongKeyInitialEntityCollector<?>) new Gson().fromJson(source, classType))
                        .arg(key)
                        .build();
                context.put(key, Properties.RESULT.getValue(), result);
                continuationPossible = true;
            } catch (JsonSyntaxException ex){
                ex.printStackTrace();
                putResultIntoContext(context, createResult(Codes.FAIL_CONVERSION.getValue()));
            }
        }
    }

    private boolean checkClassTypeOrSetResult(Context context) {
        Optional<Object> maybeCollectorType = context.get(key, Properties.COLLECTOR_TYPE.getValue());
        if (maybeCollectorType.isEmpty()){
            putResultIntoContext(context, createResult(Codes.NO_COLLECTOR_TYPE.getValue()));
            return false;
        }
        classType = (Class<?>) maybeCollectorType.get();
        return true;
    }

    private boolean checkFileContentOrSetResult(Context context) {
        Optional<Object> maybeFileContent = context.get(key, sourceProperty);
        if (maybeFileContent.isEmpty()){
            putResultIntoContext(context, createResult(Codes.NO_SOURCE.getValue()));
            return false;
        }
        source = String.valueOf(maybeFileContent.get());
        return true;
    }

    private void putResultIntoContext(Context context, Result<LongKeyInitialEntityCollector<?>> result) {
        context.put(key, Properties.RESULT.getValue(), result);
    }

    private Result<LongKeyInitialEntityCollector<?>> createResult(String code) {
        return Result.<LongKeyInitialEntityCollector<?>>builder()
                .success(false)
                .code(code)
                .arg(key)
                .build();
    }

    @RequiredArgsConstructor
    @Getter
    public enum Properties{
        COLLECTOR_TYPE("task.conversion.property.collectorType"),
        RESULT("task.conversion.property.result");

        private final String value;
    }

    @RequiredArgsConstructor
    @Getter
    public enum Codes{
        NO_COLLECTOR_TYPE("task.conversion.code.noCollectorType"),
        NO_SOURCE("task.conversion.code.noSource"),
        FAIL_CONVERSION("task.conversion.code.failConversion");

        private final String value;
    }
}
