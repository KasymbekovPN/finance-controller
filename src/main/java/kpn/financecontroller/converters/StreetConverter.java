package kpn.financecontroller.converters;

import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.old.entities.StreetInitialEntity;
import kpn.financecontroller.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

// TODO: 27.02.2022 del ???
@Slf4j
@Component
@Profile("dev")
public class StreetConverter implements Converter<StreetInitialEntity, StreetEntity> {

    private final DTOService<City, CityEntity, Long> cityDTOService;

    @Autowired
    public StreetConverter(DTOService<City, CityEntity, Long> cityDTOService) {
        this.cityDTOService = cityDTOService;
    }

    @Override
    public StreetEntity convert(StreetInitialEntity value) {
        StreetEntity entity = new StreetEntity();
        entity.setId(value.getId());
        entity.setName(value.getName());

        Result<City> result = cityDTOService.loader().byId(value.getCityId());
        log.info("StreetConverter searching result: {}", result);
        if (result.getSuccess()){
            entity.setCityEntity(new CityEntity(result.getValue()));
        }
        return entity;
    }
}
