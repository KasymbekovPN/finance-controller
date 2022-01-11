package kpn.financecontroller.data.services.deleters;

import kpn.financecontroller.data.services.DTOServiceException;
import kpn.financecontroller.data.services.Operator;
import kpn.financecontroller.result.Result;

public abstract class AbstractDeleter<D, E, I> extends Operator<Void> implements Deleter<D, E, I> {

    @Override
    public Result<Void> byId(I id) {
        Result.Builder<Void> builder = getResultBuilder();
        try{
            deleteById(id);
            builder.success(true);
        } catch (DTOServiceException ex){
            builder = exceptionToResultBuilder(ex);
        }

        return builder
                .arg(getId())
                .arg(id)
                .build();
    }

    @Override
    public Result<Void> by(String attribute, Object value) {
        Result.Builder<Void> builder = getResultBuilder();
        if (checkAttribute(attribute)){
            if (checkValue(attribute, value)){
                try{
                    deleteBy(attribute, value);
                    builder.success(true);
                } catch (DTOServiceException ex){
                    builder = exceptionToResultBuilder(ex);
                }
            } else {
                builder.success(false).code("deleter.by.value.disallowed");
            }
        } else {
            builder.success(false).code("deleter.by.attribute.disallowed");
        }

        return builder
                .arg(getId())
                .arg(attribute)
                .arg(value)
                .build();
    }

    @Override
    public Result<Void> all() {
        Result.Builder<Void> builder = getResultBuilder();
        try{
            deleteAll();
            builder.success(true);
        } catch (DTOServiceException ex){
            builder = exceptionToResultBuilder(ex);
        }

        return builder.build();
    }

    protected boolean checkAttribute(String attribute) {
        return false;
    }

    protected boolean checkValue(String attribute, Object value) {
        return false;
    }

    protected void deleteById(I id) throws DTOServiceException {
        throw new DTOServiceException("deleter.deleteById.unsupported");
    }

    protected void deleteBy(String attribute, Object value) throws DTOServiceException {
        throw new DTOServiceException("deleter.deleteBy.unsupported");
    }

    protected void deleteAll() throws DTOServiceException {
        throw new DTOServiceException("deleter.deleteAll.unsupported");
    }
}
