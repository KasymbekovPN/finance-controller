package kpn.financecontroller.initialization.tasks;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.lib.domain.AbstractDomain;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import kpn.taskexecutor.lib.contexts.Context;
import lombok.Setter;

import java.util.List;

final public class CleanupTask extends BaseTask {
    @Setter
    private Service<Long, ? extends AbstractDomain<Long>, Predicate, ? extends Result<? extends List<? extends AbstractDomain<Long>>>> service;

    @Override
    public void execute(Context context) {
        reset();
        Result<? extends List<? extends AbstractDomain<Long>>> cleaningResult = service.deleter().all();
        if (cleaningResult.isSuccess()){
            continuationPossible = true;
        } else {
            calculateAndSetCode(key, Codes.DB_FAIL_CLEANING);
        }
        putResultIntoContext(context, Properties.DB_CLEANING_RESULT, null);
    }
}
