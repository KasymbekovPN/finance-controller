package kpn.financecontroller.initialization.generators.seed;

import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.tasks.FileReadingTask;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.generators.Generator;
import kpn.taskexecutor.lib.seeds.Seed;
import kpn.taskexecutor.lib.seeds.SeedImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
public class ReadingTaskSeedGenerator implements Generator {

    private final Deque<PathItem> pathItems;
    private final ValuedGenerator<String> valuedGenerator;
    private final Function<Context, ResultContextManager> managerCreator;

    private Boolean fieldValid = null;

    public static Builder builder(){
        return new Builder();
    }

    private ReadingTaskSeedGenerator(Deque<PathItem> pathItems,
                                     ValuedGenerator<String> valuedGenerator,
                                     Function<Context, ResultContextManager> managerCreator) {
        this.pathItems = pathItems;
        this.valuedGenerator = valuedGenerator;
        this.managerCreator = managerCreator;
    }

    @Override
    public Optional<Seed> getNextIfExist(Context context) {
        if (checkFields()){
            PathItem pathItem = pathItems.pollFirst();
            if (pathItem != null){
                Seed seed = new SeedImpl.Builder()
                        .type(FileReadingTask.class)
                        .field("valuedGenerator", valuedGenerator)
                        .field("managerCreator", managerCreator)
                        .field("path", pathItem.getPath())
                        .field("key", pathItem.getKey())
                        .build();
                return Optional.of(seed);
            }
        }
        return Optional.empty();
    }

    // TODO: 26.03.2022 to remake it
    private boolean checkFields() {
        if (fieldValid == null){
            if (managerCreator == null){
                log.warn("ManagerCreator is null");
                fieldValid = false;
                return false;
            }
            if (valuedGenerator == null){
                log.warn("ValuedGenerator is null");
                fieldValid = false;
                return false;
            }
            fieldValid = true;
        }
        return fieldValid;
    }

    public static class Builder{
        private final Deque<PathItem> pathItems = new ArrayDeque<>();
        private ValuedGenerator<String> valuedGenerator;
        private Function<Context, ResultContextManager> managerCreator;

        public Builder setValuedGenerator(ValuedGenerator<String> valuedGenerator){
            this.valuedGenerator = valuedGenerator;
            return this;
        }

        public Builder setManagerCreator(Function<Context, ResultContextManager> managerCreator){
            this.managerCreator = managerCreator;
            return this;
        }

        public Builder addPathItem(Valued<String> key, String path){
            pathItems.addLast(new PathItem(key, path));
            return this;
        }

        public ReadingTaskSeedGenerator build(){
            return new ReadingTaskSeedGenerator(pathItems, valuedGenerator, managerCreator);
        }
    }

    @RequiredArgsConstructor
    @Getter
    private static class PathItem{
        private final Valued<String> key;
        private final String path;
    }
}
