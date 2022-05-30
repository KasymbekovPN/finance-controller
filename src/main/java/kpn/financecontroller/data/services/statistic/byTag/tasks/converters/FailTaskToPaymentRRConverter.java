package kpn.financecontroller.data.services.statistic.byTag.tasks.converters;

import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.PaymentTask;
import kpn.financecontroller.rfunc.RRFunction;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;

import java.util.Arrays;

final public class FailTaskToPaymentRRConverter implements RRFunction<PaymentTask, Payment> {

    @Override
    public Result<Payment> apply(Result<PaymentTask> value) {
        ImmutableResult.Builder<Payment> builder = ImmutableResult.<Payment>builder()
                .success(value.isSuccess())
                .code(value.getSeed().getCode());
        Arrays.stream(value.getSeed().getArgs()).forEach(builder::arg);
        return builder.build();
    }
}
