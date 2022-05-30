package kpn.financecontroller.rfunc;

import kpn.lib.result.Result;

@FunctionalInterface
public interface RRFunction<TYPE, RESULT> {
    Result<RESULT> apply(Result<TYPE> value);
}
