package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.converter.aspect.FromAspectConverter;
import kpn.financecontroller.data.domain.City;
import kpn.financecontroller.data.services.dto.service.CityDtoDecorator;
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

class CityServiceWrapperTest {
    @Test
    void shouldCheckFinding_ServiceNull() {
        ImmutableResult<List<City>> expectedResult
                = ImmutableResult.<List<City>>fail("wrapper."+ CityServiceWrapper.class.getSimpleName() + ".service.null");

        CityServiceWrapper.unregisterService(CityDtoDecorator.class);
        Result<List<City>> result = new CityServiceWrapper().find(null);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckFinding() throws DTOException {
        ImmutableResult<List<City>> expectedResult = ImmutableResult.<List<City>>ok(List.of(createDomain()));

        CityServiceWrapper.registerService(new CityDtoDecorator(createService()));
        Result<List<City>> result = new CityServiceWrapper().find(null);

        assertThat(result).isEqualTo(expectedResult);
    }

    private Service<Long, City, Predicate, Result<List<City>>> createService() throws DTOException {
        CityExecutor executor = Mockito.mock(CityExecutor.class);
        Mockito
                .when(executor.execute(Mockito.anyObject()))
                .thenReturn(new DefaultExecutorResult<>(createDomain()));

        return new ServiceBuider<Long, City, Predicate, Result<List<City>>>(new FromAspectConverter<>())
                .predicateAspectBuidler()
                .executor(executor)
                .and().build();
    }

    private City createDomain() {
        City domain = new City();
        domain.setId(123L);
        domain.setName("name");
        return domain;
    }

    private abstract static class CityExecutor implements PredicateExecutor<Predicate, City> {}
}