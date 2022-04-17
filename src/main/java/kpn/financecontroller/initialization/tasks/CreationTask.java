package kpn.financecontroller.initialization.tasks;

import com.google.gson.JsonSyntaxException;
import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.storage.ObjectStorage;
import kpn.lib.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import lombok.Setter;

final public class CreationTask extends BaseTask {
    @Setter
    private ObjectStorageCreator objectStorageCreator;

    @Override
    public void execute(Context context) {
        reset();
        ObjectStorage storage = null;
        ResultContextManager contextManager = createContextManager(context);
        Result<String> fileReadingResult = contextManager.get(key, Properties.FILE_READING_RESULT, String.class);
        if (fileReadingResult.isSuccess()){
            try{
                storage = objectStorageCreator.create(fileReadingResult.getValue());
                continuationPossible = true;
            } catch (JsonSyntaxException ex){
                calculateAndSetCode(key, Codes.JSON_SYNTAX_EXCEPTION);
            }
        } else {
            calculateAndSetCode(key, Codes.NO_STRING_CONTENT);
        }

        putResultIntoContext(context, Properties.JSON_OBJECT_CREATION_RESULT, storage);
    }

    @FunctionalInterface
    public interface ObjectStorageCreator{
        ObjectStorage create(String source);
    }
}