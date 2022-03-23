package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.storages.TagStorage;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.lib.contexts.Context;

import java.util.function.Function;

final public class SavingTagTask extends BaseTask{

    private final DTOService<Tag, TagEntity, Long> dtoService;
    private final Long entityId;

    public SavingTagTask(Valued<String> key,
                         ValuedGenerator<String> valuedGenerator,
                         Function<Context, ResultContextManager> managerCreator,
                         DTOService<Tag, TagEntity, Long> dtoService,
                         Long entityId) {
        super(key, valuedGenerator, managerCreator);
        this.dtoService = dtoService;
        this.entityId = entityId;
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
