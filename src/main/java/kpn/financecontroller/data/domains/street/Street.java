package kpn.financecontroller.data.domains.street;

import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.domains.city.City;
import kpn.lib.domain.AbstractDomain;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Street extends AbstractDomain<Long> {
    private String name;
    private City city;

    public Street(StreetEntity entity) {
        setId(entity.getId());
        setName(entity.getName());
        setCity(new City(entity.getCityEntity()));
    }

    @Override
    public String getInfo() {
        return getName() + ", " + getCity().getInfo();
    }
}
