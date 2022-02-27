package kpn.financecontroller.initialization.old.collectors;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Optional;

// TODO: 27.02.2022 del ???
@Setter
@Getter
public class LoadDataCollectorImpl<K, E> implements LoadDataCollector<K, E> {
    private Map<K, E> entities;

    @Override
    public Optional<E> getEntity(K key) {
        return entities != null && entities.containsKey(key)
                ? Optional.of(entities.get(key))
                : Optional.empty();
    }
}
