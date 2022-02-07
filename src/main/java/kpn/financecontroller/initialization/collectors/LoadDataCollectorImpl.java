package kpn.financecontroller.initialization.collectors;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Optional;

@Setter
@Getter
public class LoadDataCollectorImpl<K, E> implements LoadDataCollector<K, E> {
    private String id;
    private Boolean deleteBefore;
    private Map<K, E> entities;

    @Override
    public Optional<E> getEntity(K key) {
        return entities != null && entities.containsKey(key)
                ? Optional.of(entities.get(key))
                : Optional.empty();
    }
}
