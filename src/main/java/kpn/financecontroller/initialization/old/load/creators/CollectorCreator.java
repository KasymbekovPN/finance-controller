package kpn.financecontroller.initialization.old.load.creators;

import kpn.financecontroller.initialization.old.collectors.LoadDataCollector;
import kpn.financecontroller.result.Result;

// TODO: 27.02.2022 del ???
public interface CollectorCreator<K, E> {
    Result<LoadDataCollector<K, E>> create(String source);
    Result<LoadDataCollector<K, E>> get();
}
