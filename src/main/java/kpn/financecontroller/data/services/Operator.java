package kpn.financecontroller.data.services;

import kpn.financecontroller.result.Result;

import java.util.Arrays;
import java.util.List;

abstract public class Operator<D> {

    protected void enrichBuilderByException(Result.Builder<?> builder, DTOServiceException ex) {
        builder.code(ex.getMessage());
        Arrays.stream(ex.getArgs()).forEach(builder::arg);
    }

    // TODO: 27.01.2022 dell ??
    protected Result.Builder<D> exceptionToResultBuilder(DTOServiceException exception){
        Result.Builder<D> builder = getResultBuilder()
                .success(false)
                .code(exception.getMessage());
        Arrays.stream(exception.getArgs()).forEach(builder::arg);
        return builder;
    }

    // TODO: 27.01.2022 del???
    protected Result.Builder<List<D>> exceptionToListResultBuilder(DTOServiceException exception){
        Result.Builder<List<D>> builder = getListResultBuilder()
                .success(false)
                .code(exception.getMessage());
        Arrays.stream(exception.getArgs()).forEach(builder::arg);
        return builder;
    }

    // TODO: 27.01.2022 del
    protected String getId() {
        return getClass().getSimpleName();
    }

    protected Result.Builder<D> getResultBuilder(){
        return Result.<D>builder();
    }

    protected Result.Builder<List<D>> getListResultBuilder(){
        return Result.<List<D>>builder();
    }
}
