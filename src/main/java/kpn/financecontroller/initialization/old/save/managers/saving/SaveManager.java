package kpn.financecontroller.initialization.old.save.managers.saving;

import kpn.financecontroller.result.Result;

// TODO: 27.02.2022 del ???
public interface SaveManager<K, E> {
    Result<Void> clearTarget();
    Result<Void> save();
    void clearCollector();
}
