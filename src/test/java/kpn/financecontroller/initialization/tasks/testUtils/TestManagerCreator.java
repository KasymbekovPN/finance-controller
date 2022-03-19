package kpn.financecontroller.initialization.tasks.testUtils;

import kpn.financecontroller.initialization.generators.valued.ValuedStringGenerator;
import kpn.financecontroller.initialization.managers.context.ContextManager;
import kpn.financecontroller.initialization.managers.context.ContextManagerImpl;
import kpn.taskexecutor.lib.contexts.Context;

import java.util.function.Function;

public class TestManagerCreator implements Function<Context, ContextManager> {
    @Override
    public ContextManager apply(Context context) {
        return new ContextManagerImpl(context, new ValuedStringGenerator());
    }
}
