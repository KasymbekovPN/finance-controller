package kpn.financecontroller.initialization.generators.seed;

import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.generators.Generator;
import kpn.taskexecutor.lib.seeds.Seed;
import kpn.taskexecutor.lib.seeds.SeedImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.config.Task;

import java.util.*;
import java.util.function.Function;

@Slf4j
final public class SavingGenerator implements Generator {

    private final ValuedGenerator<String> valuedGenerator;
    private final Function<Context, ResultContextManager> managerCreator;
    private final Valued<String> key;
    private final Class<? extends Task> type;
    private final DTOService<?, ?, Long> dtoService;
    private final Class<? extends Map<Long, ?>> storageType;

    public static Builder builder(){
        return new Builder();
    }

    private SavingGenerator(ValuedGenerator<String> valuedGenerator,
                            Function<Context, ResultContextManager> managerCreator,
                            Valued<String> key, Class<? extends Task> type,
                            DTOService<?, ?, Long> dtoService,
                            Class<? extends Map<Long, ?>> storageType) {
        this.valuedGenerator = valuedGenerator;
        this.managerCreator = managerCreator;
        this.key = key;
        this.type = type;
        this.dtoService = dtoService;
        this.storageType = storageType;
    }

    private Boolean fieldValidity;
    private Deque<Long> entityIds;

    @Override
    public Optional<Seed> getNextIfExist(Context context) {
        if (checkFields()){
            Optional<Long> maybeId = getNextEntityId(context);
            if (maybeId.isPresent()){
                Seed seed = SeedImpl.builder()
                        .type(type)
                        .field("managerCreator", managerCreator)
                        .field("valuedGenerator", valuedGenerator)
                        .field("key", key)
                        .field("dtoService", dtoService)
                        .field("entityId", maybeId.get())
                        .build();
                return Optional.of(seed);
            }
        }
        return Optional.empty();
    }

    // TODO: 28.03.2022 optimize it - to builder-like inner class
    private boolean checkFields() {
        if (fieldValidity == null){
            if (managerCreator == null){
                log.info("Manager creator is null");
                fieldValidity = false;
            } else if (valuedGenerator == null){
                log.info("Valued generator is null");
                fieldValidity = false;
            } else if (key == null){
                log.info("Key is null");
                fieldValidity = false;
            } else if (type == null){
                log.info("Type is null");
                fieldValidity = false;
            } else if (dtoService == null){
                log.info("DTO service is null");
                fieldValidity = false;
            } else if (storageType == null){
                log.info("Storage type is null");
                fieldValidity = false;
            } else {
                fieldValidity = true;
            }
        }
        return fieldValidity;
    }

    private Optional<Long> getNextEntityId(Context context) {
        if (entityIds == null){
            Result<Object> result = managerCreator.apply(context).get(key, Properties.JSON_TO_DB_CONVERSION_RESULT);
            if (result.getSuccess()){
                Set<Long> ids = storageType.cast(result.getValue()).keySet();
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
        private Class<? extends Task> type;
        private DTOService<?, ?, Long> dtoService;
        private Class<? extends Map<Long, ?>> storageType;

        public Builder managerCreator(Function<Context, ResultContextManager> managerCreator) {
            this.managerCreator = managerCreator;
            return this;
        }

        public Builder valuedGenerator(ValuedGenerator<String> valuedGenerator) {
            this.valuedGenerator = valuedGenerator;
            return this;
        }

        public Builder key(Valued<String> key) {
            this.key = key;
            return this;
        }

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
            return new SavingGenerator(valuedGenerator, managerCreator, key, type, dtoService, storageType);
        }
    }
}
