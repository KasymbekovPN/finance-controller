package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.converter.aspect.FromAspectConverter;
import kpn.financecontroller.data.domain.Seller;
import kpn.financecontroller.data.services.dto.service.SellerDtoDecorator;
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

class SellerServiceWrapperTest {
    @Test
    void shouldCheckFinding_ServiceNull() {
        ImmutableResult<List<Seller>> expectedResult
                = ImmutableResult.<List<Seller>>fail("wrapper."+ SellerServiceWrapper.class.getSimpleName() + ".service.null");

        SellerServiceWrapper.unregisterService(SellerDtoDecorator.class);
        Result<List<Seller>> result = new SellerServiceWrapper().find(null);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckFinding() throws DTOException {
        ImmutableResult<List<Seller>> expectedResult = ImmutableResult.<List<Seller>>ok(List.of(createDomain()));

        SellerServiceWrapper.registerService(new SellerDtoDecorator(createService()));
        Result<List<Seller>> result = new SellerServiceWrapper().find(null);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckFindingAll_ServiceNull() {
        ImmutableResult<List<Seller>> expectedResult
                = ImmutableResult.<List<Seller>>fail("wrapper."+ SellerServiceWrapper.class.getSimpleName() + ".service.null");

        SellerServiceWrapper.unregisterService(SellerDtoDecorator.class);
        Result<List<Seller>> result = new SellerServiceWrapper().findAll();

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckFindingAll() throws DTOException {
        ImmutableResult<List<Seller>> expectedResult = ImmutableResult.<List<Seller>>ok(List.of(createDomain()));

        SellerServiceWrapper.registerService(new SellerDtoDecorator(createService()));
        Result<List<Seller>> result = new SellerServiceWrapper().findAll();

        assertThat(result).isEqualTo(expectedResult);
    }

    private Service<Long, Seller, Predicate, Result<List<Seller>>> createService() throws DTOException {
        SellerPredicateExecutor executor = Mockito.mock(SellerPredicateExecutor.class);
        DefaultExecutorResult<Seller> result = new DefaultExecutorResult<>(createDomain());
        Mockito
                .when(executor.execute(Mockito.anyObject()))
                .thenReturn(result);

        SellerLoadingExecutor loader = Mockito.mock(SellerLoadingExecutor.class);
        Mockito
                .when(loader.load())
                .thenReturn(result);

        return new ServiceBuider<Long, Seller, Predicate, Result<List<Seller>>>(new FromAspectConverter<>())
                .predicateAspectBuidler()
                .executor(executor)
                .and()
                .loadingAspectBuilder()
                .executorAll(loader)
                .and()
                .build();
    }

    private Seller createDomain() {
        Seller domain = new Seller();
        domain.setId(123L);
        domain.setName("name");
        return domain;
    }

    private abstract static class SellerPredicateExecutor implements PredicateExecutor<Predicate, Seller> {}
    private abstract static class SellerLoadingExecutor implements CompletelyLoadingExecutor<Seller> {}
}