package kpn.financecontroller.data.entity;

import kpn.financecontroller.data.entity.CountryEntity;
import kpn.financecontroller.data.entity.RegionEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RegionEntityTest {

    @Test
    void shouldCheckIdSettingGetting() {
        long expectedId = 123L;
        RegionEntity entity = new RegionEntity();
        entity.setId(expectedId);
        assertThat(expectedId).isEqualTo(entity.getId());
    }

    @Test
    void shouldCheckNameSettingGetting() {
        String expectedName = "Moscow";
        RegionEntity entity = new RegionEntity();
        entity.setName(expectedName);
        assertThat(expectedName).isEqualTo(entity.getName());
    }

    @Test
    void shouldCheckCountrySettingGetting() {
        CountryEntity expectedCountry = new CountryEntity();
        RegionEntity entity = new RegionEntity();
        entity.setCountryEntity(expectedCountry);
        assertThat(expectedCountry).isEqualTo(entity.getCountryEntity());
    }
}