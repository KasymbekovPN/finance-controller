package kpn.financecontroller.data.propertyExtractors.checkers;

import kpn.financecontroller.result.Result;

// TODO: 02.02.2022 del
public interface Checker<T> {
    void set(T value);
    Result<T> check();
}
