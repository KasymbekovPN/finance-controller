package kpn.financecontroller.data.services.statistic.byTag.tasks.checker;

import kpn.financecontroller.data.services.statistic.byTag.tasks.task.PaymentTask;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.Task;
import kpn.financecontroller.rfunc.OFunction;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
final public class PaymentTaskTypeChecker implements OFunction<Task, PaymentTask> {

    @Override
    public Optional<PaymentTask> apply(Task value) {
        return value.getClass().equals(PaymentTask.class)
                ? Optional.of((PaymentTask) value)
                : Optional.empty();
    }
}
