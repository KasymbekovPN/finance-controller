package kpn.financecontroller.initialization.load.collectors;

import java.util.Map;
import java.util.Optional;

public interface LoadDataCollector<K, E> {
    void setDeleteBefore(Boolean deleteBefore);
    Boolean getDeleteBefore();
    void setEntities(Map<K, E> entities);
    Optional<E> getEntity(K key);
}
