package kpn.financecontroller.initialization.load.calculators;

import kpn.financecontroller.result.Result;

// TODO: 02.02.2022 replace
public interface PathCalculator<R, T> {
    Result<R> calculate(T value);
}
