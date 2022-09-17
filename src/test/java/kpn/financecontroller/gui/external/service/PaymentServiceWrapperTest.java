package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.converter.aspect.FromAspectConverter;
import kpn.financecontroller.data.domain.Payment;
import kpn.financecontroller.data.services.dto.service.PaymentDtoDecorator;
import kpn.lib.buider.ServiceBuider;
import kpn.lib.exception.DTOException;
import kpn.lib.executor.DefaultExecutorResult;
import kpn.lib.executor.loading.CompletelyLoadingExecutor;
import kpn.lib.executor.predicate.PredicateExecutor;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentServiceWrapperTest {
    @Test
    void shouldCheckFinding_ServiceNull() {
        ImmutableResult<List<Payment>> expectedResult
                = ImmutableResult.<List<Payment>>fail("wrapper."+ PaymentServiceWrapper.class.getSimpleName() + ".service.null");

        PaymentServiceWrapper.unregisterService(PaymentDtoDecorator.class);
        Result<List<Payment>> result = new PaymentServiceWrapper().find(null);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckFinding() throws DTOException {
        ImmutableResult<List<Payment>> expectedResult = ImmutableResult.<List<Payment>>ok(List.of(createDomain()));

        PaymentServiceWrapper.registerService(new PaymentDtoDecorator(createService()));
        Result<List<Payment>> result = new PaymentServiceWrapper().find(null);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckFindingAll_ServiceNull() {
        ImmutableResult<List<Payment>> expectedResult
                = ImmutableResult.<List<Payment>>fail("wrapper."+ PaymentServiceWrapper.class.getSimpleName() + ".service.null");

        PaymentServiceWrapper.unregisterService(PaymentDtoDecorator.class);
        Result<List<Payment>> result = new PaymentServiceWrapper().findAll();

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckFindingAll() throws DTOException {
        ImmutableResult<List<Payment>> expectedResult = ImmutableResult.<List<Payment>>ok(List.of(createDomain()));

        PaymentServiceWrapper.registerService(new PaymentDtoDecorator(createService()));
        Result<List<Payment>> result = new PaymentServiceWrapper().findAll();

        assertThat(result).isEqualTo(expectedResult);
    }

    private Service<Long, Payment, Predicate, Result<List<Payment>>> createService() throws DTOException {
        PaymentPredicateExecutor executor = Mockito.mock(PaymentPredicateExecutor.class);
        DefaultExecutorResult<Payment> result = new DefaultExecutorResult<>(createDomain());
        Mockito
                .when(executor.execute(Mockito.anyObject()))
                .thenReturn(result);

        PaymentLoadingExecutor loader = Mockito.mock(PaymentLoadingExecutor.class);
        Mockito
                .when(loader.load())
                .thenReturn(result);

        return new ServiceBuider<Long, Payment, Predicate, Result<List<Payment>>>(new FromAspectConverter<>())
                .predicateAspectBuidler()
                .executor(executor)
                .and()
                .loadingAspectBuilder()
                .executorAll(loader)
                .and()
                .build();
    }

    private Payment createDomain() {
        Payment domain = new Payment();
        domain.setId(123L);
        return domain;
    }

    private abstract static class PaymentPredicateExecutor implements PredicateExecutor<Predicate, Payment> {}
    private abstract static class PaymentLoadingExecutor implements CompletelyLoadingExecutor<Payment> {}
}