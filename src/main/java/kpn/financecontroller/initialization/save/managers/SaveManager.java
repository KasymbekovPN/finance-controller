package kpn.financecontroller.initialization.save.managers;

import kpn.financecontroller.result.Result;

public interface SaveManager<K, E> {
    Result<Void> clearTarget();
    Result<Void> save();
    void clearCollector();
}
