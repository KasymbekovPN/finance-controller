package kpn.financecontroller.initialization.entityUpdater;

import kpn.financecontroller.data.entities.AbstractEntity;
import kpn.financecontroller.initialization.context.Context;

public abstract class AbstractEntityUpdater<E extends AbstractEntity> implements EntityUpdater<E> {

    @Override
    public E update(Context context, E entity) {
        reset();
        if (checkAndGetCollectors() && checkAndGetMatching()){
            updateEntity(entity);
        }
        return entity;
    }

    protected abstract void reset();
    protected abstract boolean checkAndGetCollectors();
    protected abstract boolean checkAndGetMatching();
    protected abstract void updateEntity(E entity);
}
