package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import lombok.Setter;

import java.util.function.Function;

final public class CleanupTask extends BaseTask {
    @Setter
    private DTOService<?, ?, Long> dtoService;

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
        Result<Void> cleaningResult = dtoService.deleter().all();
        if (cleaningResult.getSuccess()){
            continuationPossible = true;
        } else {
            calculateAndSetCode(key, Codes.DB_FAIL_CLEANING);
        }
        putResultIntoContext(context, Properties.DB_CLEANING_RESULT, null);
    }
}
