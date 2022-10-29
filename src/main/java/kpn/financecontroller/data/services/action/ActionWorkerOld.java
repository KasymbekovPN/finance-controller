package kpn.financecontroller.data.services.action;

import kpn.lib.result.Result;

// TODO: 29.10.2022 del
public interface ActionWorkerOld {
    Result<Object> execute(String algorithm);
}
