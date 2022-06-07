package kpn.financecontroller.data.services.statistic.byTag.tasks.converters;

import kpn.financecontroller.data.domains.payment.Payment;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
final public class PaymentCalculator implements Function<Collection<Payment>, Float> {

    @Override
    public Float apply(Collection<Payment> payments) {
        return payments.stream()
                .map(Payment::getPrice)
                .collect(Collectors.toSet())
                .stream()
                .reduce(0f, Float::sum);
    }
}
