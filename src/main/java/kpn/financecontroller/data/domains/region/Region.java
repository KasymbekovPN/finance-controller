package kpn.financecontroller.data.domains.region;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.entities.region.RegionEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Region {
    private Long id;
    private String name;
    private Country country;

    public Region(RegionEntity regionEntity) {
        id = regionEntity.getId();
        name = regionEntity.getName();
        country = new Country(regionEntity.getCountryEntity());
    }

    public String getFullName(){
        return getName() + ", " + getCountry().getName();
    }
}
