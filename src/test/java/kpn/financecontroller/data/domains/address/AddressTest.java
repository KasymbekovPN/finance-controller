package kpn.financecontroller.data.domains.address;

import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.domains.street.Street;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AddressTest {

    @Test
    void shouldCheckFullNameGetting() {
        String addressName = "4";
        String streetName = "some street";
        String cityName = "some city";
        String regionName = "region name";
        String countryName = "some country";
        Country country = new Country(1L, countryName);
        Region region = new Region(1L, regionName, country);
        City city = new City(1L, cityName, region);
        Street street = new Street(1L, streetName, city);
        Address address = new Address(1L, addressName, street);

        String expectedFullName = addressName + ", " + streetName + ", " + cityName + ", " + regionName + ", " + countryName;
        Assertions.assertThat(expectedFullName).isEqualTo(address.getFullName());
    }
}