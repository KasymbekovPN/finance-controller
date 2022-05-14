package kpn.financecontroller.data.domains.country;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.ArrayDeque;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CountryTest {
    @Test
    void shouldCheckInfoGetting() {
        String expectedInfo = "country.name";
        Country country = new Country();
        country.setName(expectedInfo);

        assertThat(expectedInfo).isEqualTo(country.getInfo());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckGetting.csv")
    void shouldCheckGetting(Long id, String name, String rawPath, String expectedResult) {
        ArrayDeque<String> path = new ArrayDeque<>(List.of(rawPath.split("\\.")));
        Country country = new Country();
        country.setId(id);
        country.setName(name);

        String result = country.get(path);
        assertThat(expectedResult).isEqualTo(result);
    }
}
