package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.converter.aspect.FromAspectConverter;
import kpn.financecontroller.data.domain.Street;
import kpn.financecontroller.data.services.dto.service.StreetDtoDecorator;
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

class StreetServiceWrapperTest {
    @Test
    void shouldCheckFinding_ServiceNull() {
        ImmutableResult<List<Street>> expectedResult
                = ImmutableResult.<List<Street>>fail("wrapper."+ StreetServiceWrapper.class.getSimpleName() + ".service.null");

        StreetServiceWrapper.unregisterService(StreetDtoDecorator.class);
        Result<List<Street>> result = new StreetServiceWrapper().find(null);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckFinding() throws DTOException {
        ImmutableResult<List<Street>> expectedResult = ImmutableResult.<List<Street>>ok(List.of(createDomain()));

        StreetServiceWrapper.registerService(new StreetDtoDecorator(createService()));
        Result<List<Street>> result = new StreetServiceWrapper().find(null);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckFindingAll_ServiceNull() {
        ImmutableResult<List<Street>> expectedResult
                = ImmutableResult.<List<Street>>fail("wrapper."+ StreetServiceWrapper.class.getSimpleName() + ".service.null");

        StreetServiceWrapper.unregisterService(StreetDtoDecorator.class);
        Result<List<Street>> result = new StreetServiceWrapper().findAll();

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckFindingAll() throws DTOException {
        ImmutableResult<List<Street>> expectedResult = ImmutableResult.<List<Street>>ok(List.of(createDomain()));

        StreetServiceWrapper.registerService(new StreetDtoDecorator(createService()));
        Result<List<Street>> result = new StreetServiceWrapper().findAll();

        assertThat(result).isEqualTo(expectedResult);
    }

    private Service<Long, Street, Predicate, Result<List<Street>>> createService() throws DTOException {
        StreetPredicateExecutor executor = Mockito.mock(StreetPredicateExecutor.class);
        DefaultExecutorResult<Street> result = new DefaultExecutorResult<>(createDomain());
        Mockito
                .when(executor.execute(Mockito.anyObject()))
                .thenReturn(result);

        StreetLoadingExecutor loader = Mockito.mock(StreetLoadingExecutor.class);
        Mockito
                .when(loader.load())
                .thenReturn(result);

        return new ServiceBuider<Long, Street, Predicate, Result<List<Street>>>(new FromAspectConverter<>())
                .predicateAspectBuidler()
                .executor(executor)
                .and()
                .loadingAspectBuilder()
                .executorAll(loader)
                .and()
                .build();
    }

    private Street createDomain() {
        Street domain = new Street();
        domain.setId(123L);
        domain.setName("name");
        return domain;
    }

    private abstract static class StreetPredicateExecutor implements PredicateExecutor<Predicate, Street> {}
    private abstract static class StreetLoadingExecutor implements CompletelyLoadingExecutor<Street> {}
}