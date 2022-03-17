// TODO: 16.03.2022 del
//package kpn.financecontroller.initialization._factory.factory;
//
//import kpn.financecontroller.initialization._collector.collector.LongKeyInitialEntityCollector;
//import kpn.financecontroller.initialization._context.context.Context;
//import kpn.financecontroller.initialization._task.task.InitialEntityCollectorCreationTask;
//import kpn.financecontroller.initialization._task.task.SavingTask;
//import kpn.financecontroller.initialization._task.task.Task;
//import lombok.Setter;
//
//import java.util.*;
//import java.util.function.Function;
//
//public class SavingTaskFactory implements TaskFactory{
//
//    // TODO: 09.03.2022 it must be deque of InitItem
//    private final Deque<String> keys;
//    private final Map<String, Function<Long, SavingTask<?, ?, ?>>> creators;
//
//    @Setter
//    private Context context;
//
//    private Iterator<? extends Map.Entry<Long, ?>> iterator;
//    private String currentKey;
//
//    public SavingTaskFactory(List<String> keys, Map<String, Function<Long, SavingTask<?, ?, ?>>> creators) {
//        this.keys = new ArrayDeque<>(keys);
//        this.creators = creators;
//    }
//
//    @Override
//    public Optional<Task> getNextIfExist() {
//        if (iterator == null){
//            currentKey = keys.pollFirst();
//            if (currentKey == null){
//                return Optional.empty();
//            }
//
//            LongKeyInitialEntityCollector<?> collector
//                    = (LongKeyInitialEntityCollector<?>) context.get(currentKey, InitialEntityCollectorCreationTask.Properties.RESULT.getValue()).get();
//            iterator = collector.getEntities().entrySet().iterator();
//        }
//
//        if (iterator.hasNext()){
//            Map.Entry<Long, ?> next = iterator.next();
//            return Optional.of(creators.get(currentKey).apply(next.getKey()));
//        }
//
//        iterator = null;
//        return getNextIfExist();
//    }
//}
