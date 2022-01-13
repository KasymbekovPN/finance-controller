package kpn.financecontroller.data.domains.city;

import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.entities.city.CityEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class City {
    private Long id;
    private String name;
    private Region region;

    public City(CityEntity entity) {
        id = entity.getId();
        name = entity.getName();
        region = entity.getRegionEntity() != null ? new Region(entity.getRegionEntity()) : null;
    }
}
