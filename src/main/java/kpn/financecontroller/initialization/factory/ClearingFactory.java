package kpn.financecontroller.initialization.factory;

import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.task.ClearingTask;
import kpn.financecontroller.initialization.task.Task;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

public class ClearingFactory implements TaskFactory{

    private final Deque<InitItem> items;

    public ClearingFactory(List<InitItem> items) {
        this.items = new ArrayDeque<>(items);
    }

    @Override
    public Optional<Task> getNextIfExist() {
        InitItem item = items.pollFirst();
        return item != null ? Optional.of(new ClearingTask(item.getKey(), item.getDtoService())) : Optional.empty();
    }

    @RequiredArgsConstructor
    @Getter
    public static class InitItem{
        private final String key;
        private final DTOService<?, ?, Long> dtoService;
    }
}
