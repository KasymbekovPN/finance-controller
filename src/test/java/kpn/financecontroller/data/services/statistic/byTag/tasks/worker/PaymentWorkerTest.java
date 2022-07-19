package kpn.financecontroller.data.services.statistic.byTag.tasks.worker;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.converters.aspect.FromAspectConverter;
import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.PaymentTask;
import kpn.lib.buider.ServiceBuider;
import kpn.lib.exception.DTOException;
import kpn.lib.executor.DefaultExecutorResult;
import kpn.lib.executor.predicate.PredicateExecutor;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
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
    void shouldCheckFinding() throws DTOException {
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

    private Service<Long, Payment, Predicate, Result<List<Payment>>> createService(Predicate predicate, List<Payment> payments) throws DTOException {
        TestExecutor executor = Mockito.mock(TestExecutor.class);

        Mockito
                .when(executor.execute(predicate))
                .thenReturn(new DefaultExecutorResult<>(payments));

        return new ServiceBuider<Long, Payment, Predicate, Result<List<Payment>>>(new FromAspectConverter<>())
                .predicateAspectBuidler()
                .executor(executor)
                .and()
                .build();
    }

    private Function<PaymentTask, Predicate> createConverter(Predicate predicate) {
        TestConverter converter = Mockito.mock(TestConverter.class);

        Mockito
                .when(converter.apply(Mockito.anyObject()))
                .thenReturn(predicate);

        return converter;
    }

    abstract private static class TestExecutor implements PredicateExecutor<Predicate, Payment> {}
    abstract private static class TestConverter implements Function<PaymentTask, Predicate>{}
}