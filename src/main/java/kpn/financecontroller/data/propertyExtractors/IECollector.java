package kpn.financecontroller.data.propertyExtractors;

import java.util.Map;
import java.util.Optional;

public interface IECollector<K, E> {
    void setDeleteBefore(Boolean deleteBefore);
    Boolean getDeleteBefore();
    void setEntities(Map<K, E> entities);
    Optional<E> getEntity(K key);
}
