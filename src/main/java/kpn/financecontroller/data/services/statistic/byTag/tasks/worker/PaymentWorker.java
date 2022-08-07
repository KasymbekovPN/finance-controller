package kpn.financecontroller.data.services.statistic.byTag.tasks.worker;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domain.Payment;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.PaymentTask;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
@AllArgsConstructor
final public class PaymentWorker implements Worker<PaymentTask, Payment> {
    private final Service<Long, Payment, Predicate, Result<List<Payment>>> service;
    private final Function<PaymentTask, Predicate> converter;

    @Override
    public Result<List<Payment>> execute(PaymentTask task) {
        return service.executor().execute(converter.apply(task));
    }
}
