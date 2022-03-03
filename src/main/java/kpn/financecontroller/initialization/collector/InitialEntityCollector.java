package kpn.financecontroller.initialization.collector;

import java.util.Map;
import java.util.Optional;

public interface InitialEntityCollector<K, E> {
    void setEntities(Map<K, E> entities);
    Map<K, E> getEntities();
    Optional<E> getEntity(K key);
}
