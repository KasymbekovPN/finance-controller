package kpn.financecontroller.rfunc.checker.removing;

import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;

import java.util.List;

public abstract class AbstractBeforeRemovingChecker<T> implements RemovingChecker<T> {
    @Override
    public Result<List<T>> apply(T value) {
        return ImmutableResult.<List<T>>ok(null);
    }
}
