package kpn.financecontroller.data.loaders;

import kpn.financecontroller.result.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractLoader<D, E, I> implements Loader<D, E, I> {

    @Override
    public Result<D> byId(I id) {
        Optional<E> maybeEntity = loadById(id);
        if (maybeEntity.isPresent()){
            return getResultBuilder()
                    .success(true)
                    .value(convertEntityToDomain(maybeEntity.get()))
                    .build();
        }
        return getResultBuilder()
                .success(false)
                .code("loader.byId.noOne")
                .arg(getLoaderId())
                .arg(id)
                .build();
    }

    @Override
    public Result<List<D>> by(String attribute, Object value) {
        Result.Builder<List<D>> builder = getListResultBuilder();
        if (checkAttribute(attribute)){
            if (checkValue(attribute, value)){
                List<E> entities = loadBy(attribute, value);
                builder
                        .success(true)
                        .value(convertEntitiesToDomains(entities));
            } else {
                builder
                        .code("loader.by.disallowedValue")
                        .arg(getLoaderId())
                        .arg(attribute)
                        .arg(value);
            }
        } else {
            builder
                    .code("loader.by.disallowedAttribute")
                    .arg(getLoaderId())
                    .arg(attribute);
        }

        return builder.build();
    }

    @Override
    public Result<List<D>> all() {
        List<E> entities = loadAll();
        return getListResultBuilder()
                .success(true)
                .value(convertEntitiesToDomains(entities))
                .build();
    }

    protected String getLoaderId() {
        return getClass().getSimpleName();
    }

    protected boolean checkAttribute(String attribute){
        return false;
    }
    protected boolean checkValue(String attribute, Object value){
        return false;
    }

    protected Optional<E> loadById(I id){
        return Optional.empty();
    }

    protected List<E> loadBy(String attribute, Object value){
        return new ArrayList<>();
    }

    protected List<E> loadAll(){
        return new ArrayList<>();
    }

    protected abstract Result.Builder<D> getResultBuilder();
    protected abstract Result.Builder<List<D>> getListResultBuilder();

    protected abstract D convertEntityToDomain(E entity);
    protected abstract List<D> convertEntitiesToDomains(List<E> entity);
}
