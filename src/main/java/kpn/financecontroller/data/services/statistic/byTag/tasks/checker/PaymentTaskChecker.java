package kpn.financecontroller.data.services.statistic.byTag.tasks.checker;

import kpn.financecontroller.data.services.statistic.byTag.tasks.task.PaymentTask;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;

final public class PaymentTaskChecker implements Checker<PaymentTask> {

    @Override
    public Result<PaymentTask> check(PaymentTask task) {
        return  (task.isBeginTimeEnable() && task.getBeginTime() == null) ||
                (task.isEndTimeEnable() && task.getEndTime() == null)
                ? ImmutableResult.<PaymentTask>bFail("stat.by.tag.payment.checking.fail").value(task).build()
                : ImmutableResult.<PaymentTask>ok(task);
    }
}
