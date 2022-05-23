package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.region.Region;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BeforeCitySavingCheckerTest {

    @Test
    void shouldCheck_whenNameNull() {
        City city = new Builder().build();
        ImmutableResult<City> expectedResult = ImmutableResult.<City>fail("checking.domain.city.name.isEmpty");

        Result<City> result = new BeforeCitySavingChecker().apply(city);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenNameEmpty() {
        City city = new Builder().name("").build();
        ImmutableResult<City> expectedResult = ImmutableResult.<City>fail("checking.domain.city.name.isEmpty");

        Result<City> result = new BeforeCitySavingChecker().apply(city);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenRegionNull() {
        City city = new Builder().name("some.name").build();
        ImmutableResult<City> expectedResult = ImmutableResult.<City>fail("checking.domain.city.region.isEmpty");

        Result<City> result = new BeforeCitySavingChecker().apply(city);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck() {
        City city = new Builder().name("some.name").region(new Region()).build();
        ImmutableResult<City> expectedResult = ImmutableResult.<City>ok(city);

        Result<City> result = new BeforeCitySavingChecker().apply(city);
        assertThat(expectedResult).isEqualTo(result);
    }

    private static class Builder{

        private String name;
        private Region region;

        public City build(){
            City city = new City();
            city.setName(name);
            city.setRegion(region);
            return city;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder region(Region region) {
            this.region = region;
            return this;
        }
    }
}