package kpn.financecontroller.initialization.load.manager;

import kpn.financecontroller.initialization.load.tasks.LoadingTask;
import kpn.financecontroller.result.Result;

public interface LoadingManager {
    Result<Void> execute(LoadingTask<?, ?> loadingTask);
}
