package kpn.financecontroller.converters;

import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.entities.address.AddressEntity;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.entities.BuildingInitialEntity;
import kpn.financecontroller.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("dev")
public class BuildingConverter implements Converter<BuildingInitialEntity, AddressEntity> {

    private final DTOService<Street, StreetEntity, Long> streetDTOService;

    @Autowired
    public BuildingConverter(DTOService<Street, StreetEntity, Long> streetDTOService) {
        this.streetDTOService = streetDTOService;
    }

    @Override
    public AddressEntity convert(BuildingInitialEntity value) {
        AddressEntity entity = new AddressEntity();
        entity.setId(value.getId());
        entity.setName(value.getName());

        Result<Street> result = streetDTOService.loader().byId(value.getStreetId());
        log.info("BuildingConverter searching result: {}", result);
        if (result.getSuccess()){
            entity.setStreetEntity(new StreetEntity(result.getValue()));
        }
        return entity;
    }
}
