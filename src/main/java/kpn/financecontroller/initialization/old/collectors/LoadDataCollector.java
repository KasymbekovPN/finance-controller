package kpn.financecontroller.initialization.old.collectors;

import java.util.Map;
import java.util.Optional;

// TODO: 27.02.2022 del ???
public interface LoadDataCollector<K, E> {
    void setEntities(Map<K, E> entities);
    Map<K, E> getEntities();
    Optional<E> getEntity(K key);
}
