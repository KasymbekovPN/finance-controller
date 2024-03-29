package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domain.Country;
import kpn.financecontroller.data.domain.Region;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BeforeRegionSavingCheckerTest {

    @Test
    void shouldCheck_whenNameNull() {
        Region region = new Builder().build();
        ImmutableResult<List<Region>> expectedResult = ImmutableResult.<List<Region>>fail("checking.domain.region.name.isEmpty");

        Result<List<Region>> result = new BeforeRegionSavingChecker().apply(region);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenNameEmpty() {
        Region region = new Builder().name("").build();
        ImmutableResult<List<Region>> expectedResult = ImmutableResult.<List<Region>>fail("checking.domain.region.name.isEmpty");

        Result<List<Region>> result = new BeforeRegionSavingChecker().apply(region);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenCountryIdNull() {
        Region region = new Builder().name("some.name").build();
        ImmutableResult<List<Region>> expectedResult = ImmutableResult.<List<Region>>fail("checking.domain.region.country.isEmpty");

        Result<List<Region>> result = new BeforeRegionSavingChecker().apply(region);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck() {
        Region region = new Builder().name("some.name").country(new Country()).build();
        ImmutableResult<List<Region>> expectedResult = ImmutableResult.<List<Region>>ok(List.of(region));

        Result<List<Region>> result = new BeforeRegionSavingChecker().apply(region);
        assertThat(expectedResult).isEqualTo(result);
    }

    private static class Builder{

        private String name;
        private Country country;

        private Region build(){
            Region region = new Region();
            region.setName(name);
            region.setCountry(country);
            return region;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder country(Country country) {
            this.country = country;
            return this;
        }
    }
}