package kpn.financecontroller.data.services.dto.loaders;

import kpn.financecontroller.data.services.dto.DTOServiceExceptionOld;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// TODO: 14.07.2022 del
public abstract class AbstractLoaderOld<D, E, I> implements LoaderOld<D, E, I> {

    private final String name;

    public AbstractLoaderOld(String name) {
        this.name = name;
    }

    @Override
    public Result<D> byId(I id) {
        ImmutableResult.Builder<D> builder;
        try{
            Optional<E> maybeEntity = loadById(id);
            if (maybeEntity.isPresent()){
                builder = ImmutableResult.<D>bOk(convertEntityToDomain(maybeEntity.get()));
            } else {
                builder = ImmutableResult.<D>bFail("loader.byId.noOne");
            }
        } catch (DTOServiceExceptionOld ex){
            builder = ImmutableResult.<D>bFail(ex.getMessage());
            Arrays.stream(ex.getArgs()).forEach(builder::arg);
        }

        return builder
                .beginArg(id)
                .beginArg(name)
                .build();
    }

    @Override
    public Result<List<D>> by(String attribute, Object value) {
        ImmutableResult.Builder<List<D>> builder;
        if (checkAttribute(attribute)){
            if (checkValue(attribute, value)){
                try{
                    List<E> entities = loadBy(attribute, value);
                    builder = ImmutableResult.<List<D>>bOk(convertEntitiesToDomains(entities));
                } catch (DTOServiceExceptionOld ex){
                    builder = ImmutableResult.<List<D>>bFail(ex.getMessage());
                    Arrays.stream(ex.getArgs()).forEach(builder::arg);
                }
            } else {
                builder = ImmutableResult.<List<D>>bFail("loader.by.disallowedValue");
            }
        } else {
            builder = ImmutableResult.<List<D>>bFail("loader.by.disallowedAttribute");
        }

        return builder
                .beginArg(value)
                .beginArg(attribute)
                .beginArg(name)
                .build();
    }

    @Override
    public Result<List<D>> all() {
        ImmutableResult.Builder<List<D>> builder;
        try {
            List<E> entities = loadAll();
            builder = ImmutableResult.<List<D>>bOk(convertEntitiesToDomains(entities));
        } catch (DTOServiceExceptionOld ex){
            builder = ImmutableResult.<List<D>>bFail(ex.getMessage());
            Arrays.stream(ex.getArgs()).forEach(builder::arg);
        }

        return builder
                .beginArg(name)
                .build();
    }

    protected boolean checkAttribute(String attribute){
        return false;
    }
    protected boolean checkValue(String attribute, Object value){
        return false;
    }

    protected Optional<E> loadById(I id) throws DTOServiceExceptionOld {
        throw new DTOServiceExceptionOld("loader.loadById.unsupported");
    }

    protected List<E> loadBy(String attribute, Object value) throws DTOServiceExceptionOld {
        throw new DTOServiceExceptionOld("loader.loadBy.unsupported");
    }

    protected List<E> loadAll() throws DTOServiceExceptionOld {
        throw new DTOServiceExceptionOld("loader.loadAll.unsupported");
    }

    protected abstract D convertEntityToDomain(E entity);
    protected abstract List<D> convertEntitiesToDomains(List<E> entities);
}
