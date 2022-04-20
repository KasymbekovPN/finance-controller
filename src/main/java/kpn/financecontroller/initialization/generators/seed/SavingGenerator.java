package kpn.financecontroller.initialization.generators.seed;

import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.storage.ObjectStorage;
import kpn.financecontroller.initialization.tasks.SavingTask;
import kpn.lib.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.seed.DefaultSeed;
import kpn.taskexecutor.lib.seed.Seed;
import kpn.taskexecutor.lib.task.Task;

import java.util.*;
import java.util.function.Function;

final public class SavingGenerator extends BaseGenerator {

    private final SavingTask.Strategy strategy;

    private Deque<Long> entityIds;

    public static Builder builder(){
        return new Builder();
    }

    private SavingGenerator(Valued<String> key,
                            ValuedGenerator<String> valuedGenerator,
                            Function<Context, ResultContextManager> managerCreator,
                            SavingTask.Strategy strategy) {
        super(key, valuedGenerator, managerCreator);
        this.strategy = strategy;

        this.fieldValidator
                .toChecking(strategy, "Strategy")
                .caller(getClass().getSimpleName());
    }

    @Override
    protected Optional<Seed> getNext(Context context) {
        Optional<Long> maybeId = getNextEntityId(context);
        if (maybeId.isPresent()){
            Seed seed = DefaultSeed.builder()
                    .type(SavingTask.class)
                    .field("managerCreator", managerCreator)
                    .field("valuedGenerator", valuedGenerator)
                    .field("key", key)
                    .field("strategy", strategy)
                    .field("entityId", maybeId.get())
                    .build();
            return Optional.of(seed);
        }
        return Optional.empty();
    }

    private Optional<Long> getNextEntityId(Context context) {
        if (entityIds == null){
            Result<ObjectStorage> result = managerCreator.apply(context).get(key, Properties.JSON_TO_DB_CONVERSION_RESULT, ObjectStorage.class);
            if (result.isSuccess()){
                Set<Long> ids = result.getValue().keySet();
                entityIds = new ArrayDeque<>(ids);
            }
        }

        Long id = entityIds != null ? entityIds.pollFirst() : null;
        return id != null ? Optional.of(id) : Optional.empty();
    }

    public static class Builder extends BaseBuilder{
        private SavingTask.Strategy strategy;

        public Builder strategy(SavingTask.Strategy strategy) {
            this.strategy = strategy;
            return this;
        }

        public SavingGenerator build(){
            return new SavingGenerator(key, valuedGenerator, managerCreator, strategy);
        }
    }
}
