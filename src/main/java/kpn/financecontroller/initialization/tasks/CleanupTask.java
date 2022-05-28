package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.data.services.dto.DTOService;
import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.lib.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import lombok.Setter;

final public class CleanupTask extends BaseTask {
    @Setter
    private DTOService<?, ?> dtoService;

    @Override
    public void execute(Context context) {
        reset();
        Result<Void> cleaningResult = dtoService.deleter().all();
        if (cleaningResult.isSuccess()){
            continuationPossible = true;
        } else {
            calculateAndSetCode(key, Codes.DB_FAIL_CLEANING);
        }
        putResultIntoContext(context, Properties.DB_CLEANING_RESULT, null);
    }
}
