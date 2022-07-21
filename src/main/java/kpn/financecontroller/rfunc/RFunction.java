package kpn.financecontroller.rfunc;

import kpn.lib.result.Result;

@FunctionalInterface
public interface RFunction<T, R> {
    Result<R> apply(T value);
}
