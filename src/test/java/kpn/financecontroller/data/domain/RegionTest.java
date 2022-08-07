package kpn.financecontroller.data.domain;

import kpn.financecontroller.data.domain.Country;
import kpn.financecontroller.data.domain.Region;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RegionTest {
    @Test
    void shouldCheckInfoGetting() {
        String countryName = "country.name";
        Country country = new Country();
        country.setName(countryName);

        String regionName = "region.name";
        Region region = new Region();
        region.setName(regionName);
        region.setCountry(country);

        String expectedInfo = regionName + ", " + countryName;
        assertThat(expectedInfo).isEqualTo(region.getInfo());
    }
}