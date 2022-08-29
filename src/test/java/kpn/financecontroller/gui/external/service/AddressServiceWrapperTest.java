package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.converter.aspect.FromAspectConverter;
import kpn.financecontroller.data.domain.Address;
import kpn.financecontroller.data.services.dto.service.AddressDtoDecorator;
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

class AddressServiceWrapperTest {
    @Test
    void shouldCheckFinding_ServiceNull() {
        ImmutableResult<List<Address>> expectedResult
                = ImmutableResult.<List<Address>>fail("wrapper."+ AddressServiceWrapper.class.getSimpleName() + ".service.null");

        AddressServiceWrapper.unregisterService(AddressDtoDecorator.class);
        Result<List<Address>> result = new AddressServiceWrapper().find(null);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckFinding() throws DTOException {
        ImmutableResult<List<Address>> expectedResult = ImmutableResult.<List<Address>>ok(List.of(createDomain()));

        AddressServiceWrapper.registerService(new AddressDtoDecorator(createService()));
        Result<List<Address>> result = new AddressServiceWrapper().find(null);

        assertThat(result).isEqualTo(expectedResult);
    }

    private Service<Long, Address, Predicate, Result<List<Address>>> createService() throws DTOException {
        AddressExecutor executor = Mockito.mock(AddressExecutor.class);
        Mockito
                .when(executor.execute(Mockito.anyObject()))
                .thenReturn(new DefaultExecutorResult<>(createDomain()));

        return new ServiceBuider<Long, Address, Predicate, Result<List<Address>>>(new FromAspectConverter<>())
                .predicateAspectBuidler()
                .executor(executor)
                .and().build();
    }

    private Address createDomain() {
        Address domain = new Address();
        domain.setId(123L);
        domain.setName("name");
        return domain;
    }

    private abstract static class AddressExecutor implements PredicateExecutor<Predicate, Address> {}
}