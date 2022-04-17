package kpn.financecontroller.initialization.tasks.saving;

import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.storage.ObjectStorage;
import kpn.financecontroller.initialization.tasks.BaseTask;
import kpn.lib.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import lombok.Setter;

final public class RegionSavingTask extends BaseTask {

    @Setter
    private DTOService<Region, RegionEntity, Long> dtoService;
    @Setter
    private Long entityId;

    @Override
    public void execute(Context context) {
        reset();
        ResultContextManager contextManager = createContextManager(context);
        Result<ObjectStorage> storageResult = contextManager.get(key, Properties.JSON_TO_DB_CONVERSION_RESULT, ObjectStorage.class);
        if (storageResult.isSuccess()){
            ObjectStorage storage = storageResult.getValue();
            RegionEntity entity = (RegionEntity) storage.get(entityId);
            if (entity != null){
                Result<Region> savingResult = dtoService.saver().save(entity);
                if (savingResult.isSuccess()){
                    entity.setId(savingResult.getValue().getId()); // TODO: 11.04.2022 ???
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