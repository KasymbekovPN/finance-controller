package kpn.financecontroller.initialization.factory;

import kpn.financecontroller.initialization.task.Task;

import java.util.Optional;

public interface TaskFactory {
    Optional<Task> getNextIfExist();
}
