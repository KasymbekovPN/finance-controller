package kpn.financecontroller.rfunc.checker.saving;

import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;

import java.util.List;

public abstract class AbstractBeforeSavingChecker<T> implements SavingChecker<T> {
    @Override
    public Result<List<T>> apply(T value) {
        return ImmutableResult.<List<T>>ok(List.of(value));
    }
}
