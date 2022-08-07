package kpn.financecontroller.data.entity;

import kpn.financecontroller.data.entity.CityEntity;
import kpn.financecontroller.data.entity.RegionEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CityEntityTest {

    @Test
    void shouldCheckIdSettingGetting() {
        long expectedId = 123L;
        CityEntity entity = new CityEntity();
        entity.setId(expectedId);
        assertThat(expectedId).isEqualTo(entity.getId());
    }

    @Test
    void shouldCheckNameSettingGetting() {
        String expectedName = "Moscow";
        CityEntity entity = new CityEntity();
        entity.setName(expectedName);
        assertThat(expectedName).isEqualTo(entity.getName());
    }

    @Test
    void shouldCheckRegionSettingGetting() {
        RegionEntity expectedRegion = new RegionEntity();
        expectedRegion.setId(123L);

        CityEntity entity = new CityEntity();
        entity.setRegionEntity(expectedRegion);
        assertThat(expectedRegion).isEqualTo(entity.getRegionEntity());
    }
}