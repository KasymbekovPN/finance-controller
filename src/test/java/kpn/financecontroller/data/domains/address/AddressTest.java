package kpn.financecontroller.data.domains.address;

import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.domains.street.Street;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddressTest {

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

        String streetName = "street.name";
        Street street = new Street();
        street.setName(streetName);
        street.setCity(city);

        String addressName = "address.name";
        Address address = new Address();
        address.setName(addressName);
        address.setStreet(street);

        String expected = addressName + ", " + streetName + ", " + cityName + ", " + regionName + ", " + countryName;
        assertThat(expected).isEqualTo(address.getInfo());
    }
}