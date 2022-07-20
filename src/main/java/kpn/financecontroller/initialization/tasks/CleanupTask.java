package kpn.financecontroller.initialization.tasks;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import kpn.taskexecutor.lib.contexts.Context;
import lombok.Setter;

import java.util.List;

final public class CleanupTask extends BaseTask {
    @Setter
    private Service<Long, ?, Predicate, Result<List<?>>> service;
    // TODO: 20.07.2022 del
//    private DTOServiceOLdOld<?, ?> dtoServiceOLd;

    @Override
    public void execute(Context context) {
        reset();
        Result<List<?>> cleaningResult = service.deleter().all();
        if (cleaningResult.isSuccess()){
            continuationPossible = true;
        } else {
            calculateAndSetCode(key, Codes.DB_FAIL_CLEANING);
        }
        putResultIntoContext(context, Properties.DB_CLEANING_RESULT, null);
    }
}
