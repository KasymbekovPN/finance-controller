package kpn.financecontroller.initialization.generators.seed;

import kpn.financecontroller.data.services.dto.DTOService;
import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.tasks.CleanupTask;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.seed.DefaultSeed;
import kpn.taskexecutor.lib.seed.Seed;
import kpn.taskexecutor.lib.seed.generator.Generator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.Function;

final public class CleanupGenerator extends BaseGenerator {

    private final Deque<Item> items;

    public static Builder builder(){
        return new Builder();
    }

    private CleanupGenerator(Valued<String> key,
                             ValuedGenerator<String> valuedGenerator,
                             Function<Context, ResultContextManager> managerCreator,
                             List<Item> items) {
        super(key, valuedGenerator, managerCreator);
        this.items = new ArrayDeque<>(items);

        this.fieldValidator
                .reset()
                .toChecking(managerCreator, "Manager creator")
                .toChecking(valuedGenerator, "Valued generator")
                .caller(getClass().getSimpleName());
    }

    @Override
    protected Optional<Seed> getNext(Context context) {
        Item item = items.pollFirst();
        if (item != null){
            Seed seed = DefaultSeed.builder()
                    .type(CleanupTask.class)
                    .field("managerCreator", managerCreator)
                    .field("valuedGenerator", valuedGenerator)
                    .field("key", item.getKey())
                    .field("dtoService", item.getDtoService())
                    .build();
            return Optional.of(seed);
        }
        return Optional.empty();
    }

    public static class Builder extends BaseBuilder{
        private final List<Item> items = new ArrayList<>();

        public Builder item(Valued<String> key, DTOService<?, ?, Long> dtoService){
            items.add(new Item(key, dtoService));
            return this;
        }

        @Override
        public Generator build() {
            return new CleanupGenerator(key, valuedGenerator, managerCreator, items);
        }
    }

    @RequiredArgsConstructor
    @Getter
    private static class Item{
        private final Valued<String> key;
        private final DTOService<?, ?, Long> dtoService;
    }
}
