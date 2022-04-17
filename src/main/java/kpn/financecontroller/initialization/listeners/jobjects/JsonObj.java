package kpn.financecontroller.initialization.listeners.jobjects;

import java.util.Map;
import java.util.Optional;

public interface JsonObj<K, E> {
    void setEntities(Map<K, E> entities);
    Map<K, E> getEntities();
    Optional<E> getEntity(K key);
}
