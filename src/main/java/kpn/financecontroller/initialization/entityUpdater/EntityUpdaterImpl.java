package kpn.financecontroller.initialization.entityUpdater;

import kpn.financecontroller.initialization.context.Context;
import kpn.financecontroller.initialization.entities.AbstractInitialEntity;

public class EntityUpdaterImpl<E extends AbstractInitialEntity> implements EntityUpdater<E> {
    @Override
    public E update(String key, E entity, Context context) {
        return null;
    }

    // TODO: 10.03.2022 del
//    @Override
//    public E update(Context context, E entity) {
//        reset();
//        if (checkAndGetCollectors() && checkAndGetMatching()){
//            updateEntity(entity);
//        }
//        return entity;
//    }
//
//    protected abstract void reset();
//    protected abstract boolean checkAndGetCollectors();
//    protected abstract boolean checkAndGetMatching();
//    protected abstract void updateEntity(E entity);
}
