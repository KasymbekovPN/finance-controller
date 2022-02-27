package kpn.financecontroller.initialization.factory;

import kpn.financecontroller.initialization.task.FileReadingTask;
import kpn.financecontroller.initialization.task.Task;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

public class FileReadingTaskFactory implements TaskFactory{
    private final Deque<String> keys;

    public FileReadingTaskFactory(List<String> keys) {
        this.keys = new ArrayDeque<>(keys);
    }

    @Override
    public Optional<Task> getNextIfExist(){
        String key = keys.pollFirst();
        return key != null ? Optional.of(new FileReadingTask(key)) : Optional.empty();
    }
}
