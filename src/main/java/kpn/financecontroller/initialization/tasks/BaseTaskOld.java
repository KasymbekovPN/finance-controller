package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.tasks.Task;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Function;

// TODO: 23.03.2022 del
@RequiredArgsConstructor
public abstract class BaseTaskOld implements Task {
    protected final Valued<String> key;
    private final ValuedGenerator<String> valuedGenerator;
    private final Function<Context, ResultContextManager> managerCreator;

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

    final protected void calculateAndSetCode(Valued<String> k, Valued<String> p){
        code = valuedGenerator.generate(k, p);
    }

    final protected ResultContextManager createContextManager(Context context){
        return managerCreator.apply(context);
    }

    final <T> void putResultIntoContext(Context context, Valued<String> p, T value){
        Result.Builder<T> builder = Result.<T>builder()
                .success(continuationPossible)
                .value(value)
                .code(code);
        args.forEach(builder::arg);
        managerCreator.apply(context).put(key, p, builder.build());
    }
}
