package kpn.financecontroller.data.service;

import kpn.financecontroller.result.Result;

public abstract class AbstractSaver<D, E> implements Saver<D, E> {

    private static final String FAIL_SAVING_TEMPLATE = "service.save.fail";

    @Override
    public Result<D> save(E entity) {
        try{
            E savedEntity = saveImpl(entity);
            return getResultBuilder()
                    .success(true)
                    .value(convertEntityToDomain(savedEntity))
                    .build();
        } catch (Throwable t){
            // TODO: 05.01.2022 extend definition
            return getResultBuilder()
                    .success(false)
                    .code(FAIL_SAVING_TEMPLATE)
                    .arg(getId())
                    .build();
        }
    }

    private String getId() {
        return getClass().getSimpleName();
    }

    protected abstract E saveImpl(E entity);
    protected abstract D convertEntityToDomain(E entity);
    protected abstract Result.Builder<D> getResultBuilder();
}
