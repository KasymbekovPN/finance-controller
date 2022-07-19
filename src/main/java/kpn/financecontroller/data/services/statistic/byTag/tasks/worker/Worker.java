package kpn.financecontroller.data.services.statistic.byTag.tasks.worker;

import kpn.financecontroller.data.services.statistic.byTag.tasks.task.Task;
import kpn.lib.domain.Domain;
import kpn.lib.result.Result;

import java.util.List;

public interface Worker<T extends Task, D extends Domain<Long>> {
    Result<List<D>> execute(T task);
}