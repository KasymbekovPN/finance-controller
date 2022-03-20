package kpn.financecontroller.initialization.tasks.testUtils;

import kpn.financecontroller.initialization.generators.valued.ValuedStringGenerator;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.managers.context.ResultContextManagerImpl;
import kpn.taskexecutor.lib.contexts.Context;

import java.util.function.Function;

public class TestManagerCreator implements Function<Context, ResultContextManager> {
    @Override
    public ResultContextManager apply(Context context) {
        return new ResultContextManagerImpl(context, new ValuedStringGenerator());
    }
}
