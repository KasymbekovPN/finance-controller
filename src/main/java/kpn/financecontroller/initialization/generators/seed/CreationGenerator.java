package kpn.financecontroller.initialization.generators.seed;

import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.jsonObjs.LongKeyJsonObj;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.tasks.CreationTask;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.seed.DefaultSeed;
import kpn.taskexecutor.lib.seed.Seed;

import java.util.*;
import java.util.function.Function;

final public class CreationGenerator extends BaseGenerator {
    private final Deque<Class<? extends LongKeyJsonObj<?>>> types;

    public static Builder builder(){
        return new Builder();
    }

    private CreationGenerator(Valued<String> key,
                              ValuedGenerator<String> valuedGenerator,
                              Function<Context, ResultContextManager> managerCreator,
                              List<Class<? extends LongKeyJsonObj<?>>> types) {
        super(key, valuedGenerator, managerCreator);
        this.types = new ArrayDeque<>(types);

        this.fieldValidator
                .caller(getClass().getSimpleName());
    }

    @Override
    protected Optional<Seed> getNext(Context context) {
        Class<? extends LongKeyJsonObj<?>> type = types.pollFirst();
        if (type != null){
            Seed seed = DefaultSeed.builder()
                    .type(CreationTask.class)
                    .field("managerCreator", managerCreator)
                    .field("valuedGenerator", valuedGenerator)
                    .field("key", key)
                    .field("type", type)
                    .build();
            return Optional.of(seed);
        }
        return Optional.empty();
    }

    public static class Builder extends BaseBuilder{
        private final List<Class<? extends LongKeyJsonObj<?>>> types = new ArrayList<>();

        public Builder type(Class<? extends LongKeyJsonObj<?>> type) {
            types.add(type);
            return this;
        }

        public CreationGenerator build(){
            return new CreationGenerator(key, valuedGenerator, managerCreator, types);
        }
    }
}
