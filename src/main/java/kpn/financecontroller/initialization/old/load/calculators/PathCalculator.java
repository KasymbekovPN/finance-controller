package kpn.financecontroller.initialization.old.load.calculators;

import kpn.financecontroller.result.Result;

// TODO: 27.02.2022 del ???
// TODO: 02.02.2022 replace
public interface PathCalculator<R, T> {
    Result<R> calculate(T value);
}
