// TODO: 30.05.2022 restore
//package kpn.financecontroller.data.services.statistic.byTag.tasks.worker;
//
//import kpn.financecontroller.data.domains.Domain;
//import kpn.financecontroller.data.services.statistic.byTag.tasks.checker.Checker;
//import kpn.financecontroller.data.services.statistic.byTag.tasks.task.Task;
//import kpn.lib.result.Result;
//import lombok.AllArgsConstructor;
//
//@AllArgsConstructor
//abstract public class AbstractWorker<TASK extends Task, DOMAIN extends Domain> implements Worker<TASK, DOMAIN> {
//    private final Checker<TASK> checker;
//
//    @Override
//    public Result<DOMAIN> execute(TASK task) {
//        Result<TASK> checkingResult = checker.check(task);
//        return checkingResult.isSuccess()
//                ? executeTask(task)
//                : convertResult(checkingResult);
//    }
//
//    protected abstract Result<DOMAIN> executeTask(TASK task);
//
//    // TODO: 30.05.2022 convert must separated bean
//    protected abstract Result<DOMAIN> convertResult(Result<TASK> checkingResult);
//}
