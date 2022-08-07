package kpn.financecontroller.data.domain;

import kpn.financecontroller.data.entity.CityEntity;
import kpn.lib.domain.AbstractDomain;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class City extends AbstractDomain<Long> {
    private String name;
    private Region region;

    public City(CityEntity entity) {
        setId(entity.getId());
        setName(entity.getName());
        setRegion(new Region(entity.getRegionEntity()));
    }

    @Override
    public String getInfo() {
        return getName() + ", " + getRegion().getInfo();
    }
}
