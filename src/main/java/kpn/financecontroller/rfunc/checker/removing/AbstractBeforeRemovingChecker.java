package kpn.financecontroller.rfunc.checker.removing;

import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;

abstract public class AbstractBeforeRemovingChecker<TYPE> implements RemovingChecker<TYPE> {
    @Override
    public Result<Void> apply(TYPE value) {
        return ImmutableResult.<Void>ok(null);
    }
}
