// TODO: 16.03.2022 del
//package kpn.financecontroller.initialization._factory.factory;
//
//import kpn.financecontroller.initialization._context.context.Context;
//import kpn.financecontroller.initialization._task.task.InitialEntityCollectorCreationTask;
//import kpn.financecontroller.initialization._task.task.FileReadingTask;
//import kpn.financecontroller.initialization._task.task.Task;
//import lombok.Setter;
//
//import java.util.ArrayDeque;
//import java.util.Deque;
//import java.util.List;
//import java.util.Optional;
//
//public class InitialEntityCollectorCreationTaskFactory implements TaskFactory{
//
//    private final Deque<String> keys;
//
//    @Setter
//    private Context context;
//
//    public InitialEntityCollectorCreationTaskFactory(List<String> keys) {
//        this.keys = new ArrayDeque<>(keys);
//    }
//
//    @Override
//    public Optional<Task> getNextIfExist() {
//        String key = keys.pollFirst();
//        return key != null
//                ? Optional.of(new InitialEntityCollectorCreationTask(key, FileReadingTask.Properties.RESULT.getValue()))
//                : Optional.empty();
//    }
//}
