package kpn.financecontroller.data.services.savers;

import kpn.financecontroller.data.services.DTOServiceException;
import kpn.financecontroller.data.services.Operator;
import kpn.financecontroller.result.Result;

public abstract class AbstractSaver<D, E, I> extends Operator<D> implements Saver<D, E, I> {

    private final String name;

    public AbstractSaver(String name) {
        this.name = name;
    }

    @Override
    public Result<D> save(E entity) {
        Result.Builder<D> builder = getResultBuilder()
                .arg(name);
        try{
            E savedEntity = saveImpl(entity);
            builder.success(true).value(convertEntityToDomain(savedEntity));
        } catch (DTOServiceException ex){
            enrichBuilderByException(builder, ex);
        }

        return builder.build();
    }

    protected E saveImpl(E entity) throws DTOServiceException{
        throw new DTOServiceException("saver.saveImpl.unsupported");
    }

    protected abstract D convertEntityToDomain(E entity);
}
