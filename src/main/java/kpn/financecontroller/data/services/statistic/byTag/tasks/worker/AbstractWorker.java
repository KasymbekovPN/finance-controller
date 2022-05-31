//package kpn.financecontroller.data.services.statistic.byTag.tasks.worker;
//
//import kpn.financecontroller.data.domains.Domain;
//import kpn.financecontroller.data.services.statistic.byTag.tasks.checker.Checker;
//import kpn.financecontroller.data.services.statistic.byTag.tasks.task.Task;
//import kpn.financecontroller.rfunc.RRFunction;
//import kpn.lib.result.Result;
//import lombok.AllArgsConstructor;
//
//@AllArgsConstructor
//abstract public class AbstractWorker<TASK extends Task, DOMAIN extends Domain> implements Worker<TASK, DOMAIN> {
//    private final Checker<TASK> checker;
//    private final RRFunction<TASK, DOMAIN> converter;
//
//    @Override
//    public Result<DOMAIN> execute(TASK task) {
//        Result<TASK> checkingResult = checker.check(task);
//        return checkingResult.isSuccess()
//                ? executeTask(task)
//                : converter.apply(checkingResult);
//    }
//
//    protected abstract Result<DOMAIN> executeTask(TASK task);
//}
