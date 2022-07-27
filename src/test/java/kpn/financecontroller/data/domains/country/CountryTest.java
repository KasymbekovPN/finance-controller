package kpn.financecontroller.data.domains.country;

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
