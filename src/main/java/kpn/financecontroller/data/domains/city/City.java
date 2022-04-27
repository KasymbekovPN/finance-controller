package kpn.financecontroller.data.domains.city;

import kpn.financecontroller.data.domains.AbstractDomain;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.entities.city.CityEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class City extends AbstractDomain {
    private String name;
    private Region region;

    public City(CityEntity entity) {
        id = entity.getId();
        name = entity.getName();
        region = entity.getRegionEntity() != null ? new Region(entity.getRegionEntity()) : null;
    }

    @Override
    public String getInfo() {
        return getName() + ", " + getRegion().getInfo();
    }
}
