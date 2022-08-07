package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domain.Address;
import kpn.financecontroller.data.domain.Street;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BeforeAddressSavingCheckerTest {

    @Test
    void shouldCheck_whenNameNull() {
        Address domain = new Builder().build();
        ImmutableResult<List<Address>> expectedResult = ImmutableResult.<List<Address>>fail("checking.domain.address.name.isEmpty");

        Result<List<Address>> result = new BeforeAddressSavingChecker().apply(domain);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenNameEmpty() {
        Address domain = new Builder().name("").build();
        ImmutableResult<List<Address>> expectedResult = ImmutableResult.<List<Address>>fail("checking.domain.address.name.isEmpty");

        Result<List<Address>> result = new BeforeAddressSavingChecker().apply(domain);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenStreetNull() {
        Address domain = new Builder().name("name").build();
        ImmutableResult<List<Address>> expectedResult = ImmutableResult.<List<Address>>fail("checking.domain.address.street.isEmpty");

        Result<List<Address>> result = new BeforeAddressSavingChecker().apply(domain);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck() {
        Address domain = new Builder().name("name").street(new Street()).build();
        ImmutableResult<List<Address>> expectedResult = ImmutableResult.<List<Address>>ok(List.of(domain));

        Result<List<Address>> result = new BeforeAddressSavingChecker().apply(domain);
        assertThat(expectedResult).isEqualTo(result);
    }

    private static class Builder{
        private String name;
        private Street street;

        public Address build() {
            Address address = new Address();
            address.setName(name);
            address.setStreet(street);
            return address;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder street(Street street) {
            this.street = street;
            return this;
        }
    }
}