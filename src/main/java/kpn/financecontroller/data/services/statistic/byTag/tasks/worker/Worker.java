package kpn.financecontroller.data.services.statistic.byTag.tasks.worker;

import kpn.financecontroller.data.domains.Domain;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.Task;
import kpn.lib.result.Result;

import java.util.List;

public interface Worker<TASK extends Task, DOMAIN extends Domain> {
    Result<List<DOMAIN>> execute(TASK task);
}