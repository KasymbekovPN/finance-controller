package kpn.financecontroller.checkers;

import kpn.financecontroller.result.Result;

public interface Checker {
    Result<Void> check();
}
