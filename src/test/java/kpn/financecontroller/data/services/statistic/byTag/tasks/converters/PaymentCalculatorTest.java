package kpn.financecontroller.data.services.statistic.byTag.tasks.converters;

import kpn.financecontroller.data.domain.Payment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

class PaymentCalculatorTest {

    @Test
    void shouldCheckCalculation() {
        Set<Float> prices = Set.of(123f, 234f, 345f);
        Set<Payment> payments = prices.stream()
                .map(price -> {
                    Payment payment = new Payment();
                    payment.setPrice(price);
                    return payment;
                })
                .collect(Collectors.toSet());

        Float expectedTotalPrice = prices.stream().reduce(0f, Float::sum);
        Float totalPrice = new PaymentCalculator().apply(payments);

        Assertions.assertThat(expectedTotalPrice).isEqualTo(totalPrice);
    }
}