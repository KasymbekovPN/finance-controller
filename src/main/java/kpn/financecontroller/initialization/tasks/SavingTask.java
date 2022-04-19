package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.storage.ObjectStorage;
import kpn.lib.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import lombok.Setter;

import java.util.Optional;

final public class SavingTask extends BaseTask{
    @Setter
    private Long entityId;
    @Setter
    private Strategy strategy;

    @Override
    public void execute(Context context) {
        reset();
        ResultContextManager contextManager = createContextManager(context);
        Result<ObjectStorage> storageResult = contextManager.get(key, Properties.JSON_TO_DB_CONVERSION_RESULT, ObjectStorage.class);
        if (storageResult.isSuccess()){
            Object value = storageResult.getValue().get(entityId);
            if (value != null){
                Optional<Codes> maybeCode = strategy.save(value);
                if (maybeCode.isPresent()){
                    calculateAndSetCode(key, maybeCode.get());
                } else {
                    continuationPossible = true;
                }
            } else {
                calculateAndSetCode(key, Codes.ENTITY_NOT_EXIST_ON_SAVING);
            }
        } else {
            calculateAndSetCode(key, Codes.CONVERSION_RESULT_NOT_EXIST_ON_SAVING);
        }

        putResultIntoContext(context, Properties.SAVING_RESULT, null);
    }

    @FunctionalInterface
    public interface Strategy{
        Optional<Codes> save(Object value);
    }
}
