package kpn.financecontroller.data.domains.street;

import kpn.financecontroller.data.domains.AbstractDomain;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.domains.city.City;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Street extends AbstractDomain {
    private String name;
    private City city;

    public Street(StreetEntity entity) {
        id = entity.getId();
        name = entity.getName();
        city = entity.getCityEntity() != null ? new City(entity.getCityEntity()) : null;
    }

    @Override
    public String getInfo() {
        return getName() + ", " + getCity().getInfo();
    }
}
