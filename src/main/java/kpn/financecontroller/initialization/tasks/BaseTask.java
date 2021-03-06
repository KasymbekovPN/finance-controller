package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.lib.result.ImmutableResult;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.task.Task;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Function;

@Slf4j
public abstract class BaseTask implements Task {
    @Setter
    protected Valued<String> key;
    @Setter
    protected ValuedGenerator<String> valuedGenerator;
    @Setter
    protected Function<Context, ResultContextManager> managerCreator;
    @Getter
    protected boolean continuationPossible;
    protected String code;
    protected List<Object> args;

    protected final void reset(){
        continuationPossible = false;
        code = "";
        args = List.of(key);
    }

    final protected void calculateAndSetCode(Valued<String> k, Valued<String> p){
        code = valuedGenerator.generate(k, p);
    }

    final protected ResultContextManager createContextManager(Context context){
        return managerCreator.apply(context);
    }

    final protected <T> void putResultIntoContext(Context context, Valued<String> p, T value){
        if (!continuationPossible){
            log.error("{} : {}", code, args);
        }
        ImmutableResult.Builder<T> builder = ImmutableResult.<T>builder()
                .success(continuationPossible)
                .value(value)
                .code(code);
        args.forEach(builder::arg);
        managerCreator.apply(context).put(key, p, builder.build());
    }
}
