package kpn.financecontroller.data.services.statistic.byTag.tasks.executor;

import kpn.financecontroller.data.services.statistic.byTag.tasks.task.Task;
import kpn.lib.domain.Domain;
import kpn.lib.result.Result;

import java.util.List;

public interface TaskExecutor<T extends Task, D extends Domain<Long>> {
    Result<List<D>> execute(T task);
}
