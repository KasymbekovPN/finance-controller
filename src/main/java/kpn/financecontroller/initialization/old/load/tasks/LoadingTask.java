package kpn.financecontroller.initialization.old.load.tasks;

import kpn.financecontroller.initialization.old.collectors.LoadDataCollector;
import kpn.financecontroller.result.Result;

// TODO: 27.02.2022 del ???
public interface LoadingTask<K, E> {
    Result<String> calculatePath();
    Result<Void> fillCollector(String jsonContent);
    LoadDataCollector<K, E> getCollector();
}
