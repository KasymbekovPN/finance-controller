package kpn.financecontroller.data.services;

import kpn.financecontroller.result.Result;

import java.util.Arrays;
import java.util.List;

abstract public class Operator<D> {

    protected Result.Builder<D> exceptionToResultBuilder(DTOServiceException exception){
        Result.Builder<D> builder = getResultBuilder()
                .success(false)
                .code(exception.getMessage());
        Arrays.stream(exception.getArgs()).forEach(builder::arg);
        return builder;
    }

    protected Result.Builder<List<D>> exceptionToListResultBuilder(DTOServiceException exception){
        Result.Builder<List<D>> builder = getListResultBuilder()
                .success(false)
                .code(exception.getMessage());
        Arrays.stream(exception.getArgs()).forEach(builder::arg);
        return builder;
    }

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
