package kpn.financecontroller.initialization.executor;

import kpn.financecontroller.initialization.factory.TaskFactory;
import kpn.financecontroller.result.Result;

public interface Executor {
    Result<Void> execute(TaskFactory factory);
}
