package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.managers.context.ContextManager;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.tasks.Task;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public abstract class BaseTask implements Task {
    protected final Valued<String> key;
    private final ValuedGenerator<String> valuedGenerator;
    private final Function<Context, ContextManager> managerCreator;

    @Getter
    protected boolean continuationPossible;
    protected String code;
    protected List<Object> args;

    @Override
    public void execute(Context context) {
        continuationPossible = false;
        code = null;
        args = List.of(key);
    }

    final protected void calculateCode(Valued<String> k, Valued<String> p){
        code = valuedGenerator.generate(k, p);
    }

    final protected ContextManager createContextManager(Context context){
        return managerCreator.apply(context);
    }

    final <T> void putResultIntoContext(Context context, T value){
        Result.Builder<T> builder = Result.<T>builder()
                .success(continuationPossible)
                .value(value)
                .code(code);
        args.forEach(builder::arg);
        managerCreator.apply(context).put(key, Properties.RESULT, builder.build());
    }
}
