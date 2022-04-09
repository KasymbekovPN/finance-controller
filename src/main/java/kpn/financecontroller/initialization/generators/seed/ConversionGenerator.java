package kpn.financecontroller.initialization.generators.seed;

import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.jsonObjs.LongKeyJsonObj;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.lib.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.seed.DefaultSeed;
import kpn.taskexecutor.lib.seed.Seed;
import kpn.taskexecutor.lib.task.Task;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

final public class ConversionGenerator extends BaseGenerator {
    private final Class<? extends Task> type;
    private final Class<? extends LongKeyJsonObj<?>> jsonObjType;

    private Deque<Long> entityIds;

    public static Builder builder(){
        return new Builder();
    }

    private ConversionGenerator(Valued<String> key,
                               ValuedGenerator<String> valuedGenerator,
                               Function<Context, ResultContextManager> managerCreator,
                               Class<? extends Task> type,
                               Class<? extends LongKeyJsonObj<?>> jsonObjType) {
        super(key, valuedGenerator, managerCreator);
        this.type = type;
        this.jsonObjType = jsonObjType;

        this.fieldValidator
                .toChecking(type, "Type")
                .toChecking(jsonObjType, "Json object type")
                .caller(getClass().getSimpleName());
    }

    @Override
    protected Optional<Seed> getNext(Context context) {
        Optional<Long> maybeId = getNextEntityId(context);
        if (maybeId.isPresent()){
            Seed seed = DefaultSeed.builder()
                    .type(type)
                    .field("managerCreator", managerCreator)
                    .field("valuedGenerator", valuedGenerator)
                    .field("key", key)
                    .field("entityId", maybeId.get())
                    .build();
            return Optional.of(seed);
        }
        return Optional.empty();
    }

    private Optional<Long> getNextEntityId(Context context) {
        if (entityIds == null){
            Result<Object> result = managerCreator.apply(context).get(key, Properties.JSON_OBJECT_CREATION_RESULT);
            if (result.isSuccess()){
                LongKeyJsonObj<?> jsonObj = jsonObjType.cast(result.getValue());
                Set<Long> ids = jsonObj.getEntities().keySet();
                entityIds = new ArrayDeque<>(ids);
            }
        }

        Long id = entityIds != null ? entityIds.pollFirst() : null;
        return id != null ? Optional.of(id) : Optional.empty();
    }

    public static class Builder extends BaseBuilder {
        private Class<? extends Task> type;
        private Class<? extends LongKeyJsonObj<?>> jsonObjType;

        public Builder type(Class<? extends Task> type){
            this.type = type;
            return this;
        }

        public Builder jsonObj(Class<? extends LongKeyJsonObj<?>> jsonObjType){
            this.jsonObjType = jsonObjType;
            return this;
        }

        public ConversionGenerator build(){
            return new ConversionGenerator(key, valuedGenerator, managerCreator, type, jsonObjType);
        }
    }
}
