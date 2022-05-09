package kpn.financecontroller.data.domains.region;

import kpn.financecontroller.data.domains.country.Country;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;

import java.util.ArrayDeque;
import java.util.List;

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

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckGetting.csv")
    void shouldCheckGetting(Long id, String name, String countryAnswer, String rawPath, String expectedResult) {
        ArrayDeque<String> path = new ArrayDeque<>(List.of(rawPath.split("\\.")));
        Region region = new Region();
        region.setId(id);
        region.setName(name);
        region.setCountry(createCountry(countryAnswer));

        String result = region.get(path);
        assertThat(expectedResult).isEqualTo(result);
    }

    private Country createCountry(String countryAnswer) {
        Country country = Mockito.mock(Country.class);
        Mockito
                .when(country.get(Mockito.any()))
                .thenReturn(countryAnswer);
        return country;
    }
}