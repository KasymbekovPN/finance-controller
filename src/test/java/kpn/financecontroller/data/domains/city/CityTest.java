package kpn.financecontroller.data.domains.city;

import kpn.financecontroller.data.domains.region.Region;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CityTest {
    @Test
    void shouldCheckGetting() {
        long expectedId = 123L;
        String expectedName = "Moscow";
        Region expectedRegion = new Region();

        City city = new City(expectedId, expectedName, expectedRegion);
        assertThat(expectedId).isEqualTo(city.getId());
        assertThat(expectedName).isEqualTo(city.getName());
        assertThat(expectedRegion).isEqualTo(city.getRegion());
    }
}