package kpn.financecontroller.initialization.load.tasks;

import kpn.financecontroller.initialization.load.collectors.LoadDataCollector;
import kpn.financecontroller.result.Result;

public interface LoadingTask<K, E> {
    Result<String> calculatePath();
    Result<Void> fillCollector(String jsonContent);
    LoadDataCollector<K, E> getCollector();
}
