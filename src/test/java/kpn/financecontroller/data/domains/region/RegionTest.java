package kpn.financecontroller.data.domains.region;

import kpn.financecontroller.data.domains.country.Country;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RegionTest {
    @Test
    void shouldCheckGetting() {
        long expectedId = 123L;
        String expectedName = "Moscow";
        Country expectedCountry = new Country(1L,"Russia");

        Region region = new Region(expectedId, expectedName, expectedCountry);
        assertThat(expectedId).isEqualTo(region.getId());
        assertThat(expectedName).isEqualTo(region.getName());
        assertThat(expectedCountry).isEqualTo(region.getCountry());
    }
}