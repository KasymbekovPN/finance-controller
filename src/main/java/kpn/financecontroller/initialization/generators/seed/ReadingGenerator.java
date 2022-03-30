package kpn.financecontroller.initialization.generators.seed;

import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.tasks.ReadingTask;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.seeds.Seed;
import kpn.taskexecutor.lib.seeds.SeedImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.function.Function;

final public class ReadingGenerator extends BaseGenerator {

    private final Deque<PathItem> pathItems;

    public static Builder builder(){
        return new Builder();
    }

    public ReadingGenerator(Valued<String> key,
                            ValuedGenerator<String> valuedGenerator,
                            Function<Context, ResultContextManager> managerCreator,
                            Deque<PathItem> pathItems) {
        super(key, valuedGenerator, managerCreator);
        this.pathItems = pathItems;

        this.fieldValidator
                .reset()
                .toChecking(managerCreator, "Manager creator")
                .toChecking(valuedGenerator, "Valued generator")
                .caller(getClass().getSimpleName());
    }

    @Override
    protected Optional<Seed> getNext(Context context) {
        PathItem pathItem = pathItems.pollFirst();
        if (pathItem != null){
            Seed seed = new SeedImpl.Builder()
                    .type(ReadingTask.class)
                    .field("valuedGenerator", valuedGenerator)
                    .field("managerCreator", managerCreator)
                    .field("path", pathItem.getPath())
                    .field("key", pathItem.getKey())
                    .build();
            return Optional.of(seed);
        }
        return Optional.empty();
    }

    public static class Builder extends BaseBuilder{
        private final Deque<PathItem> pathItems = new ArrayDeque<>();

        public Builder pathItem(Valued<String> key, String path){
            pathItems.addLast(new PathItem(key, path));
            return this;
        }

        public ReadingGenerator build(){
            return new ReadingGenerator(key, valuedGenerator, managerCreator, pathItems);
        }
    }

    @RequiredArgsConstructor
    @Getter
    private static class PathItem{
        private final Valued<String> key;
        private final String path;
    }
}
