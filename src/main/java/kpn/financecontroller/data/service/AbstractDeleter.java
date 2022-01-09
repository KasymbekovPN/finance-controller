package kpn.financecontroller.data.service;

import kpn.financecontroller.result.Result;

public abstract class AbstractDeleter<D, E, I> implements Deleter<D, E, I> {

    @Override
    public Result<Void> byId(I id) {
        deleteById(id);
        return Result.<Void>builder()
                .success(true)
                .build();
    }

    @Override
    public Result<Void> by(String attribute, Object value) {
        Result.Builder<Void> builder = Result.<Void>builder();
        if (checkAttribute(attribute)){
            if (checkValue(attribute, value)){
                deleteBy(attribute, value);
                builder.success(true);
            } else {
                builder
                        .success(false)
                        .code("deleter.by.value.disallowed")
                        .arg(getId())
                        .arg(attribute)
                        .arg(value);
            }
        } else {
            builder
                    .success(false)
                    .code("deleter.by.attribute.disallowed")
                    .arg(getId())
                    .arg(attribute);
        }
        return builder.build();
    }

    @Override
    public Result<Void> all() {
        deleteAll();
        return Result.<Void>builder().success(true).build();
    }

    protected String getId() {
        return getClass().getSimpleName();
    }

    protected boolean checkAttribute(String attribute) {
        return false;
    }

    protected boolean checkValue(String attribute, Object value) {
        return false;
    }

    protected void deleteById(I id){}
    protected void deleteBy(String attribute, Object value){}
    protected void deleteAll(){}
}
