package kpn.financecontroller.initialization.save.updaters;

import kpn.financecontroller.initialization.collectors.LoadDataCollector;
import kpn.financecontroller.initialization.entities.AbstractInitialEntity;

public interface CollectorUpdater<K, E extends AbstractInitialEntity<K>> {
    CollectorUpdater<K, E> reset();
    CollectorUpdater<K, E> add(K oldKey, K newKey);
    void update(LoadDataCollector<K, E> collector);
}
