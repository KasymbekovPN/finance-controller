package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.converter.aspect.FromAspectConverter;
import kpn.financecontroller.data.domain.Country;
import kpn.financecontroller.data.services.dto.service.CountryDtoDecorator;
import kpn.lib.buider.ServiceBuider;
import kpn.lib.exception.DTOException;
import kpn.lib.executor.DefaultExecutorResult;
import kpn.lib.executor.predicate.PredicateExecutor;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CountryServiceWrapperTest {

    @Test
    void shouldCheckFinding_ServiceNull() {
        ImmutableResult<List<Country>> expectedResult
                = ImmutableResult.<List<Country>>fail("wrapper."+ CountryServiceWrapper.class.getSimpleName() + ".service.null");

        CountryServiceWrapper.unregisterService(CountryDtoDecorator.class);
        Result<List<Country>> result = new CountryServiceWrapper().find(null);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckFinding() throws DTOException {
        ImmutableResult<List<Country>> expectedResult = ImmutableResult.<List<Country>>ok(List.of(createDomain()));

        CountryServiceWrapper.registerService(new CountryDtoDecorator(createService()));
        Result<List<Country>> result = new CountryServiceWrapper().find(null);

        assertThat(result).isEqualTo(expectedResult);
    }

    private Service<Long, Country, Predicate, Result<List<Country>>> createService() throws DTOException {
        CountryExecutor executor = Mockito.mock(CountryExecutor.class);
        Mockito
                .when(executor.execute(Mockito.anyObject()))
                .thenReturn(new DefaultExecutorResult<>(createDomain()));

        return new ServiceBuider<Long, Country, Predicate, Result<List<Country>>>(new FromAspectConverter<>())
                .predicateAspectBuidler()
                .executor(executor)
                .and().build();
    }

    private Country createDomain() {
        Country domain = new Country();
        domain.setId(123L);
        domain.setName("name");
        return domain;
    }

    private abstract static class CountryExecutor implements PredicateExecutor<Predicate, Country> {}
}