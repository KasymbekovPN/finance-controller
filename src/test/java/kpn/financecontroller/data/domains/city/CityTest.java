package kpn.financecontroller.data.domains.city;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.region.Region;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;

import java.util.ArrayDeque;
import java.util.List;

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

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckGetting.csv")
    void shouldCheckGetting(Long id, String name, String regionAnswer, String rawPath, String expectedResult) {
        ArrayDeque<String> path = new ArrayDeque<>(List.of(rawPath.split("\\.")));
        City domain = new City();
        domain.setId(id);
        domain.setName(name);
        domain.setRegion(createRegion(regionAnswer, path));

        String result = domain.getInDeep(path);
        assertThat(expectedResult).isEqualTo(result);
    }

    private Region createRegion(String regionAnswer, ArrayDeque<String> path) {
        Region region = Mockito.mock(Region.class);
        Mockito
                .when(region.getInDeep(path))
                .thenReturn(regionAnswer);
        return region;
    }
}