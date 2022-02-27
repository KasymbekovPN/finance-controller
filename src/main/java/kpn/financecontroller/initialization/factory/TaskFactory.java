package kpn.financecontroller.initialization.factory;

import kpn.financecontroller.initialization.context.Context;
import kpn.financecontroller.initialization.task.Task;

import java.util.Optional;

public interface TaskFactory {
    default void setContext(Context context){}
    Optional<Task> getNextIfExist();
    String getId();
}
