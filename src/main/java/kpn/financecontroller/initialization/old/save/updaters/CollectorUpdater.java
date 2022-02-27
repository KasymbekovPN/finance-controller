package kpn.financecontroller.initialization.old.save.updaters;

import kpn.financecontroller.initialization.old.collectors.LoadDataCollector;
import kpn.financecontroller.initialization.old.entities.AbstractInitialEntity;

// TODO: 27.02.2022 del ???
public interface CollectorUpdater<K, E extends AbstractInitialEntity<K>> {
    CollectorUpdater<K, E> reset();
    CollectorUpdater<K, E> add(K oldKey, K newKey);
    void update(LoadDataCollector<K, E> collector);
}
