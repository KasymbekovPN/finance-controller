package kpn.financecontroller.data.services.statistic.byTag.tasks.converters;

import kpn.financecontroller.data.domains.payment.Payment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

class PaymentCalculatorTest {

    @Test
    void shouldCheckCalculation() {
        Set<Float> prices = Set.of(123.45f, 234.56f, 345.67f);
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