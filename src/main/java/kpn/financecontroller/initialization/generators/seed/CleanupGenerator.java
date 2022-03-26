package kpn.financecontroller.initialization.generators.seed;

import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.tasks.CleanupTask;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.generators.Generator;
import kpn.taskexecutor.lib.seeds.Seed;
import kpn.taskexecutor.lib.seeds.SeedImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;

@Slf4j
final public class CleanupGenerator implements Generator {
    private final ValuedGenerator<String> valuedGenerator;
    private final Function<Context, ResultContextManager> managerCreator;
    private final Deque<DTOService<?, ?, Long>> dtoServices;

    private Boolean fieldValidity;

    public static Builder builder(){
        return new Builder();
    }

    public CleanupGenerator(ValuedGenerator<String> valuedGenerator,
                            Function<Context, ResultContextManager> managerCreator,
                            List<DTOService<?, ?, Long>> dtoServices) {
        this.valuedGenerator = valuedGenerator;
        this.managerCreator = managerCreator;
        this.dtoServices = new ArrayDeque<>(dtoServices);
    }

    @Override
    public Optional<Seed> getNextIfExist(Context context) {
        if (checkFields()){
            DTOService<?, ?, Long> dtoService = dtoServices.pollFirst();
            if (dtoService != null){
                Seed seed = SeedImpl.builder()
                        .type(CleanupTask.class)
                        .field("managerCreator", managerCreator)
                        .field("valuedGenerator", valuedGenerator)
                        .field("dtoService", dtoService)
                        .build();
                return Optional.of(seed);
            }
        }
        return Optional.empty();
    }

    private boolean checkFields() {
        if (fieldValidity == null){
            if (managerCreator == null){
                log.warn("Manager creator is null");
                fieldValidity = false;
            } else if (valuedGenerator == null){
                log.warn("Valued generator is null");
                fieldValidity = false;
            } else {
                fieldValidity = true;
            }
        }
        return fieldValidity;
    }

    public static class Builder {
        private final List<DTOService<?, ?, Long>> dtoServices = new ArrayList<>();

        private ValuedGenerator<String> valuedGenerator;
        private Function<Context, ResultContextManager> managerCreator;

        public Builder managerCreator(Function<Context, ResultContextManager> managerCreator){
            this.managerCreator = managerCreator;
            return this;
        }

        public Builder valuedGenerator(ValuedGenerator<String> valuedGenerator){
            this.valuedGenerator = valuedGenerator;
            return this;
        }

        public Builder dtoService(DTOService<?, ?, Long> type){
            dtoServices.add(type);
            return this;
        }

        public CleanupGenerator build(){
            return new CleanupGenerator(valuedGenerator, managerCreator, dtoServices);
        }
    }
}
