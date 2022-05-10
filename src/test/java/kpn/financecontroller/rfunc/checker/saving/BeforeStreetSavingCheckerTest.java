package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.street.Street;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BeforeStreetSavingCheckerTest {

    @Test
    void shouldCheck_whenNameNull() {
        Street domain = new Builder().build();
        ImmutableResult<Street> expectedResult = ImmutableResult.<Street>fail("checking.domain.street.name.isEmpty").build();

        Result<Street> result = new BeforeStreetSavingChecker().apply(domain);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenNameEmpty() {
        Street domain = new Builder().name("").build();
        ImmutableResult<Street> expectedResult = ImmutableResult.<Street>fail("checking.domain.street.name.isEmpty").build();

        Result<Street> result = new BeforeStreetSavingChecker().apply(domain);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenRegionNull() {
        Street domain = new Builder().name("name").build();
        ImmutableResult<Street> expectedResult = ImmutableResult.<Street>fail("checking.domain.street.city.isEmpty").build();

        Result<Street> result = new BeforeStreetSavingChecker().apply(domain);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck() {
        Street domain = new Builder().name("name").city(new City()).build();
        ImmutableResult<Street> expectedResult = ImmutableResult.<Street>ok(domain).build();

        Result<Street> result = new BeforeStreetSavingChecker().apply(domain);
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