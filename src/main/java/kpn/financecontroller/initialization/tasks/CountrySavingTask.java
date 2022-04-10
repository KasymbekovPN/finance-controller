package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.storages.CountryStorage;
import kpn.lib.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import lombok.Setter;

final public class CountrySavingTask extends BaseTask{

    @Setter
    private DTOService<Country, CountryEntity, Long> dtoService;
    @Setter
    private Long entityId;

    @Override
    public void execute(Context context) {
        reset();
        ResultContextManager contextManager = createContextManager(context);
        Result<CountryStorage> tagStorageResult = contextManager.get(key, Properties.JSON_TO_DB_CONVERSION_RESULT, CountryStorage.class);
        if (tagStorageResult.isSuccess()){
            CountryStorage storage = tagStorageResult.getValue();
            CountryEntity entity = storage.get(entityId);
            if (entity != null){
                Result<Country> savingResult = dtoService.saver().save(entity);
                if (savingResult.isSuccess()){
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
