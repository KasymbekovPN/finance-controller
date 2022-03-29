package kpn.financecontroller.initialization.generators.seed;

import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.generators.Generator;
import kpn.taskexecutor.lib.seeds.Seed;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.function.Function;

public abstract class BaseGenerator implements Generator {
    protected final FieldValidator fieldValidator = new FieldValidator();
    protected final Valued<String> key;
    protected final ValuedGenerator<String> valuedGenerator;
    protected final Function<Context, ResultContextManager> managerCreator;

    public BaseGenerator(Valued<String> key,
                         ValuedGenerator<String> valuedGenerator,
                         Function<Context, ResultContextManager> managerCreator) {
        this.key = key;
        this.valuedGenerator = valuedGenerator;
        this.managerCreator = managerCreator;

        fieldValidator
                .toChecking(managerCreator, "Manager creator")
                .toChecking(valuedGenerator, "Valued generator")
                .toChecking(key, "Key");
    }

    @Override
    public Optional<Seed> getNextIfExist(Context context) {
        return checkFields() ? getNext(context) : Optional.empty();
    }

    private boolean checkFields() {
        if (fieldValidator.isNotDone()){
            fieldValidator.validate();
        }
        return fieldValidator.isValid();
    }

    protected abstract Optional<Seed> getNext(Context context);

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
    protected static class FieldValidator{
        private final Deque<Item> items = new ArrayDeque<>();

        @Getter
        private boolean notDone = true;
        @Getter
        private boolean valid;
        private String caller = "";

        public FieldValidator caller(String caller){
            this.caller = caller;
            return this;
        }

        public FieldValidator toChecking(Object object, String mark){
            items.add(new Item(object, mark));
            return this;
        }

        public FieldValidator validate(){
            notDone = false;
            valid = true;
            do {
                Item item = items.pollFirst();
                if (item != null && item.getObject() == null){
                    valid = false;
                    log.warn("{} : {} is null", caller, item.getMark());
                }
            } while (items.size() != 0);
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
