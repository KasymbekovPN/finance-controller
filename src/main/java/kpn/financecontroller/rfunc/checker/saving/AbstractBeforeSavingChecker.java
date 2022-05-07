package kpn.financecontroller.rfunc.checker.saving;

import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;

abstract public class AbstractBeforeSavingChecker<TYPE> implements SavingChecker<TYPE> {

    @Override
    public Result<TYPE> apply(TYPE value) {
        return ImmutableResult.<TYPE>ok(value).build();
    }
}
