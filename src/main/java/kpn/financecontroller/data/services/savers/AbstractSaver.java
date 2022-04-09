package kpn.financecontroller.data.services.savers;

import kpn.financecontroller.data.services.DTOServiceException;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;

import java.util.Arrays;

public abstract class AbstractSaver<D, E, I> implements Saver<D, E, I> {

    private final String name;

    public AbstractSaver(String name) {
        this.name = name;
    }

    @Override
    public Result<D> save(E entity) {
        ImmutableResult.Builder<D> builder;
        try{
            E savedEntity = saveImpl(entity);
            builder = ImmutableResult.<D>ok(convertEntityToDomain(savedEntity));
        } catch (DTOServiceException ex){
            builder = ImmutableResult.<D>fail(ex.getMessage());
            Arrays.stream(ex.getArgs()).forEach(builder::arg);
        }

        return builder
                .beginArg(name)
                .build();
    }

    protected E saveImpl(E entity) throws DTOServiceException{
        throw new DTOServiceException("saver.saveImpl.unsupported");
    }

    protected abstract D convertEntityToDomain(E entity);
}
