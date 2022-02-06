package kpn.financecontroller.initialization.load.creators;

import kpn.financecontroller.initialization.load.collectors.LoadDataCollector;
import kpn.financecontroller.result.Result;

public interface CollectorCreator<K, E> {
    Result<LoadDataCollector<K, E>> create(String source);
    Result<LoadDataCollector<K, E>> get();
}
