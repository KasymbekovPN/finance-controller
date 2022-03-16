package kpn.financecontroller.converters;

import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization._old.old.entities.CityInitialEntity;
import kpn.financecontroller.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

// TODO: 27.02.2022 del???
@Slf4j
@Component
@Profile("dev")
public class CityConverter implements Converter<CityInitialEntity, CityEntity> {

    private final DTOService<Region, RegionEntity, Long> regionDTOService;

    public CityConverter(DTOService<Region, RegionEntity, Long> regionDTOService) {
        this.regionDTOService = regionDTOService;
    }

    @Override
    public CityEntity convert(CityInitialEntity value) {
        CityEntity entity = new CityEntity();
        entity.setId(value.getId());
        entity.setName(value.getName());

        Result<Region> result = regionDTOService.loader().byId(value.getRegionId());
        log.info("CityConverter searching result: {}", result);
        if (result.getSuccess()){
            entity.setRegionEntity(new RegionEntity(result.getValue()));
        }
        return entity;
    }
}
