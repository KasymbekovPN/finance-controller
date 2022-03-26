package kpn.financecontroller.initialization.generators.seed;

import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.jsonObjs.TagLongKeyJsonObj;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.tasks.JsonToDbConversionTagTask;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.generators.Generator;
import kpn.taskexecutor.lib.seeds.Seed;
import kpn.taskexecutor.lib.seeds.SeedImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Slf4j
public class TagConversionGenerator implements Generator {
    private final ValuedGenerator<String> valuedGenerator;
    private final Function<Context, ResultContextManager> managerCreator;
    private final Valued<String> key;

    private Boolean fieldValidity;
    private Deque<Long> entityIds;

    public static Builder builder(){
        return new Builder();
    }

    private TagConversionGenerator(ValuedGenerator<String> valuedGenerator,
                                   Function<Context, ResultContextManager> managerCreator,
                                   Valued<String> key) {
        this.valuedGenerator = valuedGenerator;
        this.managerCreator = managerCreator;
        this.key = key;
    }

    @Override
    public Optional<Seed> getNextIfExist(Context context) {
        if (checkFields()){
            Optional<Long> maybeId = getNextEntityId(context);
            if (maybeId.isPresent()){
                Seed seed = SeedImpl.builder()
                        .type(JsonToDbConversionTagTask.class)
                        .field("managerCreator", managerCreator)
                        .field("valuedGenerator", valuedGenerator)
                        .field("entityId", maybeId.get())
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
            } else if(valuedGenerator == null) {
                log.warn("Valued generator is null");
                fieldValidity = false;
            } else if (key == null){
                log.warn("Key is null");
                fieldValidity = false;
            } else {
                fieldValidity = true;
            }
        }
        return fieldValidity;
    }

    private Optional<Long> getNextEntityId(Context context) {
        if (entityIds == null){
            Result<TagLongKeyJsonObj> result = managerCreator.apply(context).get(key, Properties.JSON_OBJECT_CREATION_RESULT, TagLongKeyJsonObj.class);
            if (result.getSuccess()){
                Set<Long> ids = result.getValue().getEntities().keySet();
                entityIds = new ArrayDeque<>(ids);
                Long id = entityIds.pollFirst();
                if (id != null){
                    return Optional.of(id);
                }
            }
        }
        return Optional.empty();
    }

    public static class Builder {
        private ValuedGenerator<String> valuedGenerator;
        private Function<Context, ResultContextManager> managerCreator;
        private Valued<String> key;

        public Builder valuedGenerator(ValuedGenerator<String> valuedGenerator){
            this.valuedGenerator = valuedGenerator;
            return this;
        }

        public Builder managerCreator(Function<Context, ResultContextManager> managerCreator){
            this.managerCreator = managerCreator;
            return this;
        }

        public Builder key(Valued<String> key){
            this.key = key;
            return this;
        }

        public TagConversionGenerator build(){
            return new TagConversionGenerator(valuedGenerator, managerCreator, key);
        }
    }
}
