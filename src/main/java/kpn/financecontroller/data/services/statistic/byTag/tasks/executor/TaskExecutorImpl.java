package kpn.financecontroller.data.services.statistic.byTag.tasks.executor;

import kpn.financecontroller.data.services.statistic.byTag.tasks.checker.Checker;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.Task;
import kpn.financecontroller.data.services.statistic.byTag.tasks.worker.Worker;
import kpn.financecontroller.rfunc.RRFunction;
import kpn.lib.domain.Domain;
import kpn.lib.result.Result;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
final public class TaskExecutorImpl<TASK extends Task, DOMAIN extends Domain<Long>> implements TaskExecutor<TASK, DOMAIN> {
    private final Checker<TASK> checker;
    private final RRFunction<TASK, List<DOMAIN>> converter;
    private final Worker<TASK, DOMAIN> worker;

    @Override
    public Result<List<DOMAIN>> execute(TASK task) {
        Result<TASK> checkingResult = checker.check(task);
        return checkingResult.isSuccess()
                ? worker.execute(task)
                : converter.apply(checkingResult);
    }
}
