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

    private static final String CODE__CONTEXT_NO_COLLECTOR_TYPE = "task.code.noCollectorType";
    private static final String CODE__CONTEXT_NO_SOURCE = "task.code.noSource";
    private static final String CODE__CONVERSION_FAIL = "task.code.conversionFail";

    private static final String PROPERTY__CLASS_TYPE = "task.property.classType";
    private static final String PROPERTY__CONVERSION_RESULT = "task.property.conversionResult";
    private static final String PROPERTY__FILE_CONTENT = "file.reading.result";

    @Getter
    private final String key;
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
                context.put(key, PROPERTY__CONVERSION_RESULT, result);
                continuationPossible = true;
            } catch (JsonSyntaxException ex){
                ex.printStackTrace();
                putResultIntoContext(context, createResult(CODE__CONVERSION_FAIL));
            }
        }
    }

    private boolean checkClassTypeOrSetResult(Context context) {
        Optional<Object> maybeCollectorType = context.get(key, PROPERTY__CLASS_TYPE);
        if (maybeCollectorType.isEmpty()){
            putResultIntoContext(context, createResult(CODE__CONTEXT_NO_COLLECTOR_TYPE));
            return false;
        }
        classType = (Class<?>) maybeCollectorType.get();
        return true;
    }

    private boolean checkFileContentOrSetResult(Context context) {
        Optional<Object> maybeFileContent = context.get(key, PROPERTY__FILE_CONTENT);
        if (maybeFileContent.isEmpty()){
            putResultIntoContext(context, createResult(CODE__CONTEXT_NO_SOURCE));
            return false;
        }
        source = String.valueOf(maybeFileContent.get());
        return true;
    }

    private void putResultIntoContext(Context context, Result<LongKeyInitialEntityCollector<?>> result) {
        context.put(key, PROPERTY__CONVERSION_RESULT, result);
    }

    private Result<LongKeyInitialEntityCollector<?>> createResult(String code) {
        return Result.<LongKeyInitialEntityCollector<?>>builder()
                .success(false)
                .code(code)
                .arg(key)
                .build();
    }
}
