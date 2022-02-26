package kpn.financecontroller.initialization.collectors;

import java.util.Map;
import java.util.Optional;

public interface LoadDataCollector<K, E> {
    void setEntities(Map<K, E> entities);
    Map<K, E> getEntities();
    Optional<E> getEntity(K key);
}