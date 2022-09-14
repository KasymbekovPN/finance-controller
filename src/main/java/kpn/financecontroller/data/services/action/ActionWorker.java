package kpn.financecontroller.data.services.action;

import kpn.lib.result.Result;

public interface ActionWorker {
    Result<Object> execute(String algorithm);
}
