package kpn.financecontroller.data.propertyExtractors;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Optional;

@Setter
public class IECollectorImpl<K, E> implements IECollector<K, E> {
    @Getter
    private Boolean deleteBefore;
    private Map<K, E> entities;

    @Override
    public Optional<E> getEntity(K key) {
        return entities != null && entities.containsKey(key)
                ? Optional.of(entities.get(key))
                : Optional.empty();
    }
}
