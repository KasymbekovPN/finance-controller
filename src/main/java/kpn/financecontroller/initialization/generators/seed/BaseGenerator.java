package kpn.financecontroller.initialization.generators.seed;

import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.generators.Generator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Function;

public abstract class BaseGenerator implements Generator {
    protected final Valued<String> key;
    protected final ValuedGenerator<String> valuedGenerator;
    protected final Function<Context, ResultContextManager> managerCreator;

    public BaseGenerator(Valued<String> key,
                         ValuedGenerator<String> valuedGenerator,
                         Function<Context, ResultContextManager> managerCreator) {
        this.key = key;
        this.valuedGenerator = valuedGenerator;
        this.managerCreator = managerCreator;
    }

    public abstract static class BaseBuilder {
        protected Valued<String> key;
        protected ValuedGenerator<String> valuedGenerator;
        protected Function<Context, ResultContextManager> managerCreator;

        public BaseBuilder key(Valued<String> key){
            this.key = key;
            return this;
        }

        public BaseBuilder valuedGenerator(ValuedGenerator<String> valuedGenerator){
            this.valuedGenerator = valuedGenerator;
            return this;
        }

        public BaseBuilder managerCreator(Function<Context, ResultContextManager> managerCreator){
            this.managerCreator = managerCreator;
            return this;
        }

        public abstract Generator build();
    }

    @Slf4j
    @RequiredArgsConstructor
    protected static class FieldValidator{
        private final Deque<Item> items = new ArrayDeque<>();
        private final Class<?> caller;

        @Getter
        private boolean done;
        @Getter
        private boolean valid;

        public FieldValidator addToChecking(Object object, String mark){
            items.addLast(new Item(object, mark));
            return this;
        }

        public FieldValidator validate(){
            done = valid = true;
            do {
                Item item = items.pollFirst();
                if (item != null && item.getObject() == null){
                    log.warn("{} : {} is null", caller.getSimpleName(), item.getMark());
                }
            } while (items.size() == 0);
            return this;
        }

        @RequiredArgsConstructor
        @Getter
        private static class Item{
            private final Object object;
            private final String mark;
        }
    }
}
