package kpn.financecontroller.data.entities.city;

import kpn.financecontroller.data.entities.region.RegionEntity;
import org.assertj.core.api.Assertions;
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