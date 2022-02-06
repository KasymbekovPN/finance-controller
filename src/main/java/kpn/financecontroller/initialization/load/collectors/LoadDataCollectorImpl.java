package kpn.financecontroller.initialization.load.collectors;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Optional;

@Setter
public class LoadDataCollectorImpl<K, E> implements LoadDataCollector<K, E> {
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