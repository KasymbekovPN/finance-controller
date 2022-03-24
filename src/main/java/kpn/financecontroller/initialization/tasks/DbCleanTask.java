package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import lombok.Setter;

final public class DbCleanTask extends BaseTask {
    @Setter
    private DTOService<?, ?, Long> dtoService;

    @Override
    public void execute(Context context) {
        super.execute(context);
        Result<Void> cleaningResult = dtoService.deleter().all();
        if (cleaningResult.getSuccess()){
            continuationPossible = true;
        } else {
            calculateAndSetCode(key, Codes.DB_FAIL_CLEANING);
        }
        putResultIntoContext(context, Properties.DB_CLEANING_RESULT, null);
    }
}
