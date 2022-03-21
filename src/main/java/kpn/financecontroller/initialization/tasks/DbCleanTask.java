package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.lib.contexts.Context;

import java.util.function.Function;

final public class DbCleanTask extends BaseTask {
    private final DTOService<?, ?, Long> dtoService;
    public DbCleanTask(Valued<String> key,
                       ValuedGenerator<String> valuedGenerator,
                       Function<Context, ResultContextManager> managerCreator,
                       DTOService<?, ?, Long> dtoService) {
        super(key, valuedGenerator, managerCreator);
        this.dtoService = dtoService;
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
