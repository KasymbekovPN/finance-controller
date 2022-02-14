package kpn.financecontroller.converters;

import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.initialization.entities.CountryInitialEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CountryConverterTest {

    @Test
    void checkConversion() {
        CountryInitialEntity initialEntity = new CountryInitialEntity();
        long id = 123L;
        initialEntity.setId(id);
        String name = "name";
        initialEntity.setName(name);

        CountryConverter converter = new CountryConverter();
        CountryEntity entity = converter.convert(initialEntity);
        assertThat(initialEntity.getId()).isEqualTo(entity.getId());
        assertThat(initialEntity.getName()).isEqualTo(entity.getName());
    }
}