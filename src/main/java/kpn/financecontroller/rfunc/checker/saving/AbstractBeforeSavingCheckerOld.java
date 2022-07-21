package kpn.financecontroller.rfunc.checker.saving;

import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;

// TODO: 21.07.2022 del
abstract public class AbstractBeforeSavingCheckerOld<TYPE> implements SavingCheckerOld<TYPE> {

    @Override
    public Result<TYPE> apply(TYPE value) {
        return ImmutableResult.<TYPE>ok(value);
    }
}
