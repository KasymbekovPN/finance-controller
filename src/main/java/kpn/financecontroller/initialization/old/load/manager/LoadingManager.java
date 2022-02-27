package kpn.financecontroller.initialization.old.load.manager;

import kpn.financecontroller.initialization.old.load.tasks.LoadingTask;
import kpn.financecontroller.result.Result;

// TODO: 27.02.2022 del ???
public interface LoadingManager {
    Result<Void> execute(LoadingTask<?, ?> loadingTask);
}
