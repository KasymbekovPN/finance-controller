package kpn.financecontroller.initialization.generators.seed;

import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.jsonObjs.LongKeyJsonObj;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.tasks.JsonObjCreationTask;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.generators.Generator;
import kpn.taskexecutor.lib.seeds.Seed;
import kpn.taskexecutor.lib.seeds.SeedImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;

@Slf4j
public class TagCreationGenerator implements Generator {
    private final ValuedGenerator<String> valuedGenerator;
    private final Function<Context, ResultContextManager> managerCreator;
    private final Deque<Class<? extends LongKeyJsonObj<?>>> types;

    private Boolean fieldValidity;

    public static Builder builder(){
        return new Builder();
    }

    private TagCreationGenerator(ValuedGenerator<String> valuedGenerator,
                                 Function<Context, ResultContextManager> managerCreator,
                                 List<Class<? extends LongKeyJsonObj<?>>> types) {
        this.valuedGenerator = valuedGenerator;
        this.managerCreator = managerCreator;
        this.types = new ArrayDeque<>(types);
    }

    @Override
    public Optional<Seed> getNextIfExist(Context context) {
        if (checkFields()){
            Class<? extends LongKeyJsonObj<?>> type = types.pollFirst();
            if (type != null){
                Seed seed = SeedImpl.builder()
                        .type(JsonObjCreationTask.class)
                        .field("managerCreator", managerCreator)
                        .field("valuedGenerator", valuedGenerator)
                        .field("type", type)
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
        private final List<Class<? extends LongKeyJsonObj<?>>> types = new ArrayList<>();

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

        public TagCreationGenerator build(){
            return new TagCreationGenerator(valuedGenerator, managerCreator, types);
        }

        public Builder type(Class<? extends LongKeyJsonObj<?>> type) {
            types.add(type);
            return this;
        }
    }
}
