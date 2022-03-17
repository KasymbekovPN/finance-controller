// TODO: 16.03.2022 del
//package kpn.financecontroller.initialization._entityUpdater.entityUpdater;
//
//import kpn.financecontroller.initialization._context.context.Context;
//import kpn.financecontroller.initialization._entities.entities.AbstractInitialEntity;
//
//public abstract class AbstractEntityUpdater<E extends AbstractInitialEntity> implements EntityUpdater<E> {
//
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
//}
