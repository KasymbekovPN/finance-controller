package kpn.financecontroller.data.services.statistic.byTag.tasks.checker;

import kpn.financecontroller.data.services.statistic.byTag.tasks.task.Task;
import kpn.lib.result.Result;

public interface Checker<T extends Task> {
    Result<T> check(T task);
}
