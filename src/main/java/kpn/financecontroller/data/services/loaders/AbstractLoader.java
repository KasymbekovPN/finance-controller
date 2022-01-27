package kpn.financecontroller.data.services.loaders;

import kpn.financecontroller.data.services.DTOServiceException;
import kpn.financecontroller.data.services.Operator;
import kpn.financecontroller.result.Result;

import java.util.List;
import java.util.Optional;

public abstract class AbstractLoader<D, E, I> extends Operator<D> implements Loader<D, E, I> {

    private final String name;

    public AbstractLoader(String name) {
        this.name = name;
    }

    @Override
    public Result<D> byId(I id) {
        Result.Builder<D> builder = getResultBuilder()
                .arg(name)
                .arg(id);
        try{
            Optional<E> maybeEntity = loadById(id);
            if (maybeEntity.isPresent()){
                builder
                        .success(true)
                        .value(convertEntityToDomain(maybeEntity.get()));
            } else {
                builder
                        .success(false)
                        .code("loader.byId.noOne");
            }
        } catch (DTOServiceException exception){
            enrichBuilderByException(builder, exception);
        }
        return builder.build();
    }

    @Override
    public Result<List<D>> by(String attribute, Object value) {
        Result.Builder<List<D>> builder = getListResultBuilder()
                .arg(name)
                .arg(attribute)
                .arg(value);
        if (checkAttribute(attribute)){
            if (checkValue(attribute, value)){
                try{
                    List<E> entities = loadBy(attribute, value);
                    builder
                            .success(true)
                            .value(convertEntitiesToDomains(entities));
                } catch (DTOServiceException exception){
                    enrichBuilderByException(builder, exception);
                }
            } else {
                builder.code("loader.by.disallowedValue");
            }
        } else {
            builder.code("loader.by.disallowedAttribute");
        }

        return builder.build();
    }

    @Override
    public Result<List<D>> all() {
        Result.Builder<List<D>> builder = getListResultBuilder()
                .arg(name);
        try {
            List<E> entities = loadAll();
            builder
                    .success(true)
                    .value(convertEntitiesToDomains(entities))
                    .build();
        } catch (DTOServiceException ex){
            enrichBuilderByException(builder, ex);
        }

        return builder.build();
    }

    protected boolean checkAttribute(String attribute){
        return false;
    }
    protected boolean checkValue(String attribute, Object value){
        return false;
    }

    protected Optional<E> loadById(I id) throws DTOServiceException{
        throw new DTOServiceException("loader.loadById.unsupported");
    }

    protected List<E> loadBy(String attribute, Object value) throws DTOServiceException{
        throw new DTOServiceException("loader.loadBy.unsupported");
    }

    protected List<E> loadAll() throws DTOServiceException {
        throw new DTOServiceException("loader.loadAll.unsupported");
    }

    protected abstract D convertEntityToDomain(E entity);
    protected abstract List<D> convertEntitiesToDomains(List<E> entities);
}
