package kpn.financecontroller.converters;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization._old.old.entities.RegionInitialEntity;
import kpn.financecontroller.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

// TODO: 27.02.2022 del ??
@Slf4j
@Component
@Profile("dev")
public class RegionConverter implements Converter<RegionInitialEntity, RegionEntity>{

    private final DTOService<Country, CountryEntity, Long> countryDTOService;

    @Autowired
    public RegionConverter(DTOService<Country, CountryEntity, Long> countryDTOService) {
        this.countryDTOService = countryDTOService;
    }

    @Override
    public RegionEntity convert(RegionInitialEntity value) {
        RegionEntity entity = new RegionEntity();
        entity.setId(value.getId());
        entity.setName(value.getName());

        Result<Country> countrySearchingResult = countryDTOService.loader().byId(value.getCountryId());
        log.info("RegionConverter searching result: {}", countrySearchingResult);
        if (countrySearchingResult.getSuccess()){
            entity.setCountryEntity(new CountryEntity(countrySearchingResult.getValue()));
        }
        return entity;
    }
}
