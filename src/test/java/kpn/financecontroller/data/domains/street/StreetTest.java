package kpn.financecontroller.data.domains.street;

import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.region.Region;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StreetTest {
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

        Street street = new Street();
        String streetName = "street.name";
        street.setName(streetName);
        street.setCity(city);

        String expected = streetName + ", " + cityName + ", " + regionName + ", " + countryName;
        assertThat(expected).isEqualTo(street.getInfo());
    }
}