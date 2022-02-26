package kpn.financecontroller.converters;

import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.initialization.entities.CountryInitialEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class CountryConverter implements Converter<CountryInitialEntity, CountryEntity> {
    @Override
    public CountryEntity convert(CountryInitialEntity value) {
        CountryEntity entity = new CountryEntity();
        entity.setId(value.getId());
        entity.setName(value.getName());
        return entity;
    }
}