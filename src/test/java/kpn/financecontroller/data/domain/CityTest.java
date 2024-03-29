package kpn.financecontroller.data.domain;

import kpn.financecontroller.data.domain.City;
import kpn.financecontroller.data.domain.Country;
import kpn.financecontroller.data.domain.Region;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CityTest {
    @Test
    void shouldInfoGetting() {
        String countryName = "country.name";
        Country country = new Country();
        country.setName(countryName);

        String regionName = "region.name";
        Region region = new Region();
        region.setName(regionName);
        region.setCountry(country);

        String cityName = "city.name";
        City city = new City();
        city.setName(cityName);
        city.setRegion(region);

        String expected = cityName + ", " + regionName + ", " + countryName;
        assertThat(expected).isEqualTo(city.getInfo());
    }
}