package kpn.financecontroller.initialization.converter;

import kpn.financecontroller.data.entities.AbstractEntity;
import kpn.financecontroller.initialization.entities.AbstractInitialEntity;

public interface EntityConverter<T extends AbstractInitialEntity, R extends AbstractEntity> {
    R convert(T value);
}
