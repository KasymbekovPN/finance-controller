package kpn.financecontroller.initialization.entityUpdater;

import kpn.financecontroller.initialization.context.Context;
import kpn.financecontroller.initialization.entities.AbstractInitialEntity;

public interface EntityUpdater<E extends AbstractInitialEntity> {
    E update(String key, E entity, Context context);
}
