package kpn.financecontroller.initialization.factory;

import kpn.financecontroller.initialization.collector.LongKeyInitialEntityCollector;
import kpn.financecontroller.initialization.context.Context;
import kpn.financecontroller.initialization.task.ConversionTask;
import kpn.financecontroller.initialization.task.SavingTask;
import kpn.financecontroller.initialization.task.Task;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.function.Function;

public class SavingTaskFactory implements TaskFactory{

    private final Deque<InitItem> items;

    @Setter
    private Context context;

    private Iterator<? extends Map.Entry<Long, ?>> iterator;
    private InitItem currentInitItem;

    public SavingTaskFactory(List<InitItem> items) {
        this.items =  new ArrayDeque<>(items);
    }

    @Override
    public Optional<Task> getNextIfExist() {
        if (iterator == null){
            currentInitItem = items.pollFirst();
            if (currentInitItem == null){
                return Optional.empty();
            }

            LongKeyInitialEntityCollector<?> collector
                    = (LongKeyInitialEntityCollector<?>) context.get(currentInitItem.getKey(), ConversionTask.Properties.RESULT.getValue()).get();
            iterator = collector.getEntities().entrySet().iterator();
        }

        if (iterator.hasNext()){
            Map.Entry<Long, ?> next = iterator.next();
            return Optional.of(currentInitItem.getCreator().apply(next.getKey()));
        }

        iterator = null;
        return getNextIfExist();
    }

    @Override
    public String getId() {
        return getClass().getSimpleName();
    }

    @RequiredArgsConstructor
    @Getter
    public static class InitItem{
        private final String key;
        private final Function<Long, SavingTask<?, ?, ?>> creator;
    }
}
