package kpn.financecontroller.data.services;

import kpn.financecontroller.result.Result;

import java.util.Arrays;
import java.util.List;

abstract public class Operator<D> {

    protected void enrichBuilderByException(Result.Builder<?> builder, DTOServiceException ex) {
        builder.code(ex.getMessage());
        Arrays.stream(ex.getArgs()).forEach(builder::arg);
    }

    protected Result.Builder<D> getResultBuilder(){
        return Result.<D>builder();
    }

    protected Result.Builder<List<D>> getListResultBuilder(){
        return Result.<List<D>>builder();
    }
}
