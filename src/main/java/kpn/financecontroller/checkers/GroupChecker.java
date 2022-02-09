package kpn.financecontroller.checkers;

import kpn.financecontroller.result.Result;

public interface GroupChecker<T> {
    GroupChecker<T> reset();
    GroupChecker<T> set(String key, T value);
    Result<Void> check();
}
