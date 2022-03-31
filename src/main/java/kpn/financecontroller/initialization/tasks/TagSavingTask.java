package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.generators.valued.*;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.storages.TagStorage;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import lombok.Setter;

import java.util.function.Function;

final public class TagSavingTask extends BaseTask {
    @Setter
    private DTOService<Tag, TagEntity, Long> dtoService;
    @Setter
    private Long entityId;

    public void setKey(Valued<String> key){
        this.key = key;
    }

    public void setValuedGenerator(ValuedGenerator<String> valuedGenerator){
        this.valuedGenerator = valuedGenerator;
    }

    public void setManagerCreator(Function<Context, ResultContextManager> managerCreator){
        this.managerCreator = managerCreator;
    }

    @Override
    public void execute(Context context) {
        super.execute(context);

        ResultContextManager contextManager = createContextManager(context);
        Result<TagStorage> tagStorageResult = contextManager.get(key, Properties.JSON_TO_DB_CONVERSION_RESULT, TagStorage.class);
        if (tagStorageResult.getSuccess()){
            TagStorage tagStorage = tagStorageResult.getValue();
            TagEntity tagEntity = tagStorage.get(entityId);
            if (tagEntity != null){
                Result<Tag> savingResult = dtoService.saver().save(tagEntity);
                if (savingResult.getSuccess()){
                    continuationPossible = true;
                } else {
                    calculateAndSetCode(key, Codes.FAIL_SAVING_ATTEMPT);
                }
            } else {
                calculateAndSetCode(key, Codes.ENTITY_NOT_EXIST_ON_SAVING);
            }
        } else {
            calculateAndSetCode(key, Codes.CONVERSION_RESULT_NOT_EXIST_ON_SAVING);
        }

        putResultIntoContext(context, Properties.SAVING_RESULT, null);
    }
}
