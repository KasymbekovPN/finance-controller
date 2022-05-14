package kpn.financecontroller.rfunc;

import kpn.lib.result.Result;

@FunctionalInterface
public interface RFunction<TYPE, RESULT> {
    Result<RESULT> apply(TYPE value);
}
