package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domain.City;
import kpn.financecontroller.data.domain.Street;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BeforeStreetSavingCheckerTest {

    @Test
    void shouldCheck_whenNameNull() {
        Street domain = new Builder().build();
        ImmutableResult<List<Street>> expectedResult = ImmutableResult.<List<Street>>fail("checking.domain.street.name.isEmpty");

        Result<List<Street>> result = new BeforeStreetSavingChecker().apply(domain);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenNameEmpty() {
        Street domain = new Builder().name("").build();
        ImmutableResult<List<Street>> expectedResult = ImmutableResult.<List<Street>>fail("checking.domain.street.name.isEmpty");

        Result<List<Street>> result = new BeforeStreetSavingChecker().apply(domain);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenCityNull() {
        Street domain = new Builder().name("name").build();
        ImmutableResult<List<Street>> expectedResult = ImmutableResult.<List<Street>>fail("checking.domain.street.city.isEmpty");

        Result<List<Street>> result = new BeforeStreetSavingChecker().apply(domain);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck() {
        Street domain = new Builder().name("name").city(new City()).build();
        ImmutableResult<List<Street>> expectedResult = ImmutableResult.<List<Street>>ok(List.of(domain));

        Result<List<Street>> result = new BeforeStreetSavingChecker().apply(domain);
        assertThat(expectedResult).isEqualTo(result);
    }

    private static class Builder{

        private String name;
        private City city;

        public Street build() {
            Street street = new Street();
            street.setName(name);
            street.setCity(city);
            return street;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder city(City city) {
            this.city = city;
            return this;
        }
    }
}