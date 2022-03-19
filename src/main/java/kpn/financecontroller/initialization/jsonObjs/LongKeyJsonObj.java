package kpn.financecontroller.initialization.jsonObjs;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Optional;

public class LongKeyJsonObj<E> implements JsonObj<Long, E> {
    @Getter @Setter
    private Map<Long, E> entities;

    @Override
    public Optional<E> getEntity(Long key) {
        return entities.containsKey(key) ? Optional.of(entities.get(key)) : Optional.empty();
    }
}
