package kpn.financecontroller.initialization.collector;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Optional;

public class LongKeyInitialEntityCollector<E> implements InitialEntityCollector<Long, E> {

    @Getter
    @Setter
    private Map<Long, E> entities;

    @Override
    public Optional<E> getEntity(Long key) {
        return entities.containsKey(key) ? Optional.of(entities.get(key)) : Optional.empty();
    }
}
