package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.converter.aspect.FromAspectConverter;
import kpn.financecontroller.data.domain.Region;
import kpn.financecontroller.data.services.dto.service.RegionDtoDecorator;
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

class RegionServiceWrapperTest {
    @Test
    void shouldCheckFinding_ServiceNull() {
        ImmutableResult<List<Region>> expectedResult
                = ImmutableResult.<List<Region>>fail("wrapper."+ RegionServiceWrapper.class.getSimpleName() + ".service.null");

        RegionServiceWrapper.unregisterService(RegionDtoDecorator.class);
        Result<List<Region>> result = new RegionServiceWrapper().find(null);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckFinding() throws DTOException {
        ImmutableResult<List<Region>> expectedResult = ImmutableResult.<List<Region>>ok(List.of(createDomain()));

        RegionServiceWrapper.registerService(new RegionDtoDecorator(createService()));
        Result<List<Region>> result = new RegionServiceWrapper().find(null);

        assertThat(result).isEqualTo(expectedResult);
    }

    private Service<Long, Region, Predicate, Result<List<Region>>> createService() throws DTOException {
        RegionExecutor executor = Mockito.mock(RegionExecutor.class);
        Mockito
                .when(executor.execute(Mockito.anyObject()))
                .thenReturn(new DefaultExecutorResult<>(createDomain()));

        return new ServiceBuider<Long, Region, Predicate, Result<List<Region>>>(new FromAspectConverter<>())
                .predicateAspectBuidler()
                .executor(executor)
                .and().build();
    }

    private Region createDomain() {
        Region domain = new Region();
        domain.setId(123L);
        domain.setName("name");
        return domain;
    }

    private abstract static class RegionExecutor implements PredicateExecutor<Predicate, Region> {}
}