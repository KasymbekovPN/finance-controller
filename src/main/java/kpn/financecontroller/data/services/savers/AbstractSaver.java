package kpn.financecontroller.data.services.savers;

import kpn.financecontroller.data.services.DTOServiceException;
import kpn.financecontroller.data.services.Operator;
import kpn.financecontroller.result.Result;

public abstract class AbstractSaver<D, E> extends Operator<D> implements Saver<D, E> {

    @Override
    public Result<D> save(E entity) {
        Result.Builder<D> builder = getResultBuilder();
        try{
            E savedEntity = saveImpl(entity);
            builder.success(true).value(convertEntityToDomain(savedEntity));
        } catch (DTOServiceException ex){
            builder = exceptionToResultBuilder(ex);
        }

        return builder.arg(getId()).build();
    }

    protected E saveImpl(E entity) throws DTOServiceException{
        throw new DTOServiceException("saver.saveImpl.unsupported");
    }

    protected abstract D convertEntityToDomain(E entity);
}
