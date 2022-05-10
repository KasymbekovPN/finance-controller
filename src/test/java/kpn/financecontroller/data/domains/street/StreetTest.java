package kpn.financecontroller.data.domains.street;

import kpn.financecontroller.data.domains.city.City;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;

import java.util.ArrayDeque;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StreetTest {

    private static final String CITY_GET_INFO_ANSWER = "city getInfo answer";

    @Test
    void shouldInfoGetting() {
        Street street = new Street();
        String streetName = "street.name";
        street.setName(streetName);
        street.setCity(createCity(""));

        String expected = streetName + ", " + CITY_GET_INFO_ANSWER;
        assertThat(expected).isEqualTo(street.getInfo());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckGetting.csv")
    void shouldCheckGetting(Long id, String name, String cityAnswer, String rawPath, String expectedResult) {
        ArrayDeque<String> path = new ArrayDeque<>(List.of(rawPath.split("\\.")));
        Street domain = new Street();
        domain.setId(id);
        domain.setName(name);
        domain.setCity(createCity(cityAnswer));

        String result = domain.get(path);
        assertThat(expectedResult).isEqualTo(result);
    }

    private City createCity(String cityAnswer) {
        City city = Mockito.mock(City.class);
        Mockito
                .when(city.get(Mockito.any()))
                .thenReturn(cityAnswer);
        Mockito
                .when(city.getInfo())
                .thenReturn(CITY_GET_INFO_ANSWER);
        return city;
    }
}