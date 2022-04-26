package kpn.financecontroller.data.domains.region;

import kpn.financecontroller.data.domains.AbstractDomain;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.entities.region.RegionEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Region extends AbstractDomain {
    private String name;
    private Country country;

    public Region(RegionEntity regionEntity) {
        id = regionEntity.getId();
        name = regionEntity.getName();
        country = new Country(regionEntity.getCountryEntity());
    }

    @Override
    public String getInfo() {
        return getName() + ", " + getCountry().getInfo();
    }
}
