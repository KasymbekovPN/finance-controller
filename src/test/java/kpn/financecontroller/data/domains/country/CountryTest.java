package kpn.financecontroller.data.domains.country;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CountryTest {

    @Test
    void shouldCheckGetting(){
        String expectedName = "Russia";
        long expectedId = 123L;
        Country country = new Country(expectedId, expectedName);
        assertThat(expectedId).isEqualTo(country.getId());
        assertThat(expectedName).isEqualTo(country.getName());
    }
}