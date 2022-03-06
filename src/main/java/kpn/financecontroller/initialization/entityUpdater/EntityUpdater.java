package kpn.financecontroller.initialization.entityUpdater;

import kpn.financecontroller.data.entities.AbstractEntity;
import kpn.financecontroller.initialization.context.Context;

public interface EntityUpdater<E extends AbstractEntity> {
    E update(Context context, E entity);
}
