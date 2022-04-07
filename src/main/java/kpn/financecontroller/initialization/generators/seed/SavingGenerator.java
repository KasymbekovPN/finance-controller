package kpn.financecontroller.initialization.generators.seed;

import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.seed.DefaultSeed;
import kpn.taskexecutor.lib.seed.Seed;
import kpn.taskexecutor.lib.task.Task;

import java.util.*;
import java.util.function.Function;

final public class SavingGenerator extends BaseGenerator {

    private final Class<? extends Task> type;
    private final DTOService<?, ?, Long> dtoService;
    private final Class<? extends Map<Long, ?>> storageType;

    private Deque<Long> entityIds;

    public static Builder builder(){
        return new Builder();
    }

    private SavingGenerator(Valued<String> key,
                           ValuedGenerator<String> valuedGenerator,
                           Function<Context, ResultContextManager> managerCreator,
                           Class<? extends Task> type,
                           DTOService<?, ?, Long> dtoService,
                           Class<? extends Map<Long, ?>> storageType) {
        super(key, valuedGenerator, managerCreator);
        this.type = type;
        this.dtoService = dtoService;
        this.storageType = storageType;

        this.fieldValidator
                .toChecking(type, "Type")
                .toChecking(dtoService, "DTO service")
                .toChecking(storageType, "Storage type")
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
                    .field("dtoService", dtoService)
                    .field("entityId", maybeId.get())
                    .build();
            return Optional.of(seed);
        }
        return Optional.empty();
    }

    private Optional<Long> getNextEntityId(Context context) {
        if (entityIds == null){
            Result<Object> result = managerCreator.apply(context).get(key, Properties.JSON_TO_DB_CONVERSION_RESULT);
            if (result.getSuccess()){
                Set<Long> ids = storageType.cast(result.getValue()).keySet();
                entityIds = new ArrayDeque<>(ids);
            }
        }

        Long id = entityIds != null ? entityIds.pollFirst() : null;
        return id != null ? Optional.of(id) : Optional.empty();
    }

    public static class Builder extends BaseBuilder{
        private Class<? extends Task> type;
        private DTOService<?, ?, Long> dtoService;
        private Class<? extends Map<Long, ?>> storageType;

        public Builder type(Class<? extends Task> type) {
            this.type = type;
            return this;
        }

        public Builder dtoService(DTOService<?, ?, Long> dtoService) {
            this.dtoService = dtoService;
            return this;
        }

        public Builder storageType(Class<? extends Map<Long, ?>> storageType) {
            this.storageType = storageType;
            return this;
        }

        public SavingGenerator build(){
            return new SavingGenerator(key, valuedGenerator, managerCreator, type, dtoService, storageType);
        }
    }
}
