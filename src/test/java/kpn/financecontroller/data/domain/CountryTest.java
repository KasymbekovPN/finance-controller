package kpn.financecontroller.data.domain;

import kpn.financecontroller.data.domain.Country;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CountryTest {

    @Test
    void shouldCheckInfoGetting() {
        String expectedInfo = "country.name";
        Country country = new Country();
        country.setName(expectedInfo);

        assertThat(expectedInfo).isEqualTo(country.getInfo());
    }
}
