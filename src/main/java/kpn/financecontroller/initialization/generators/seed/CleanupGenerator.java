package kpn.financecontroller.initialization.generators.seed;

import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.tasks.CleanupTask;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.seed.DefaultSeed;
import kpn.taskexecutor.lib.seed.Seed;
import kpn.taskexecutor.lib.seed.generator.Generator;

import java.util.*;
import java.util.function.Function;

final public class CleanupGenerator extends BaseGenerator {

    private final Deque<DTOService<?, ?, Long>> dtoServices;

    public static Builder builder(){
        return new Builder();
    }

    private CleanupGenerator(Valued<String> key,
                             ValuedGenerator<String> valuedGenerator,
                             Function<Context, ResultContextManager> managerCreator,
                             List<DTOService<?, ?, Long>> dtoServices) {
        super(key, valuedGenerator, managerCreator);
        this.dtoServices = new ArrayDeque<>(dtoServices);

        this.fieldValidator.caller(getClass().getSimpleName());
    }

    @Override
    protected Optional<Seed> getNext(Context context) {
        DTOService<?, ?, Long> dtoService = dtoServices.pollFirst();
        if (dtoService != null){
            Seed seed = DefaultSeed.builder()
                    .type(CleanupTask.class)
                    .field("managerCreator", managerCreator)
                    .field("valuedGenerator", valuedGenerator)
                    .field("key", key)
                    .field("dtoService", dtoService)
                    .build();
            return Optional.of(seed);
        }
        return Optional.empty();
    }

    public static class Builder extends BaseBuilder{
        private final List<DTOService<?, ?, Long>> dtoServices = new ArrayList<>();

        public Builder dtoService(DTOService<?, ?, Long> type){
            dtoServices.add(type);
            return this;
        }

        @Override
        public Generator build() {
            return new CleanupGenerator(key, valuedGenerator, managerCreator, dtoServices);
        }
    }
}
