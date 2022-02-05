package kpn.financecontroller.data.propertyExtractors.manager;

import kpn.financecontroller.data.propertyExtractors.tasks.Task;
import kpn.financecontroller.result.Result;

public interface TaskManager {
    Result<Void> execute(Task<?, ?> task);
}
