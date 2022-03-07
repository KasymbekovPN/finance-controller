package kpn.financecontroller.initialization.entityUpdater;

import kpn.financecontroller.initialization.context.Context;
import kpn.financecontroller.initialization.entities.AbstractInitialEntity;

public abstract class AbstractEntityUpdater<E extends AbstractInitialEntity> implements EntityUpdater<E> {

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
