package kpn.financecontroller.data.entities.country;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CountryEntityTest {
    @Test
    void shouldCheckIdSettingGetting() {
        CountryEntity countryEntity = new CountryEntity();
        long expectedId = 123L;
        countryEntity.setId(expectedId);
        assertThat(expectedId).isEqualTo(countryEntity.getId());
    }

    @Test
    void shouldCheckNameGettingSetting() {
        String expectedName = "Russia";
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setName(expectedName);
        assertThat(expectedName).isEqualTo(countryEntity.getName());
    }
}
