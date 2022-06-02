package kpn.financecontroller.data.services.statistic.byTag.tasks.worker;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.data.entities.payment.PaymentEntity;
import kpn.financecontroller.data.services.dto.DTOService;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.PaymentTask;
import kpn.lib.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
@AllArgsConstructor
final public class PaymentWorker implements Worker<PaymentTask, Payment> {
    private final DTOService<Payment, PaymentEntity> service;
    private final Function<PaymentTask, Predicate> converter;

    @Override
    public Result<List<Payment>> execute(PaymentTask task) {
        return service.executor().execute(
                converter.apply(task)
        );
    }
}
