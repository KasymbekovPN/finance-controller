package kpn.financecontroller.data.propertyExtractors.calculators;

import kpn.financecontroller.result.Result;

// TODO: 02.02.2022 replace
public interface PathCalculator<R, T> {
    Result<R> calculate(T value);
}
