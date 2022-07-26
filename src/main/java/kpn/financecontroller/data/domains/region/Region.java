package kpn.financecontroller.data.domains.region;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.lib.domain.AbstractDomain;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Region extends AbstractDomain<Long> {
    private String name;
    private Country country;

    public Region(RegionEntity regionEntity) {
        setId(regionEntity.getId());
        setName(regionEntity.getName());
        setCountry(new Country(regionEntity.getCountryEntity()));
    }

    @Override
    public String getInfo() {
        return getName() + ", " + getCountry().getInfo();
    }
}
