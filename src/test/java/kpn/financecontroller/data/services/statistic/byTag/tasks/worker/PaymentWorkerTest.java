package kpn.financecontroller.data.services.statistic.byTag.tasks.worker;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.data.entities.payment.PaymentEntity;
import kpn.financecontroller.data.services.dto.DTOServiceOLdOld;
import kpn.financecontroller.data.services.dto.DTOServiceOLdImplOld;
import kpn.financecontroller.data.services.dto.executors.PredicateExecutorOld;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.PaymentTask;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static kpn.financecontroller.data.entities.payment.QPaymentEntity.paymentEntity;
import static org.assertj.core.api.Assertions.assertThat;

class PaymentWorkerTest {

    private static final List<Long> PAYMENT_IDS = List.of(1L, 2L);

    @Test
    void shouldCheckFinding() {
        List<Payment> payments = createPayments();
        Predicate predicate = createPredicate();

        ImmutableResult<List<Payment>> expectedResult = ImmutableResult.<List<Payment>>ok(payments);

        PaymentWorker worker = new PaymentWorker(createService(predicate, payments), createConverter(predicate));
        Result<List<Payment>> result = worker.execute(null);

        assertThat(expectedResult).isEqualTo(result);
    }

    private List<Payment> createPayments() {
        return PAYMENT_IDS.stream().map(id -> {
            Payment payment = new Payment();
            payment.setId(id);
            return payment;
        }).collect(Collectors.toList());
    }

    private Predicate createPredicate() {
        return paymentEntity.createdAt.after(LocalDate.now());
    }

    private DTOServiceOLdOld<Payment, PaymentEntity> createService(Predicate predicate, List<Payment> payments) {
        TestExecutorOld executor = Mockito.mock(TestExecutorOld.class);

        Mockito
                .when(executor.execute(predicate))
                .thenReturn(ImmutableResult.<List<Payment>>ok(payments));

        return new DTOServiceOLdImplOld<Payment, PaymentEntity>(null, null, null, executor);
    }

    private Function<PaymentTask, Predicate> createConverter(Predicate predicate) {
        TestConverter converter = Mockito.mock(TestConverter.class);

        Mockito
                .when(converter.apply(Mockito.anyObject()))
                .thenReturn(predicate);

        return converter;
    }

    abstract private static class TestExecutorOld implements PredicateExecutorOld<Payment, Predicate> {}
    abstract private static class TestConverter implements Function<PaymentTask, Predicate>{}
}