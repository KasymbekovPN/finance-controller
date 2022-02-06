package kpn.financecontroller.data.domains.street;

import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.domains.city.City;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Street {
    private Long id;
    private String name;
    private City city;

    public Street(StreetEntity entity) {
        id = entity.getId();
        name = entity.getName();
        city = entity.getCityEntity() != null ? new City(entity.getCityEntity()) : null;
    }

    public String getFullName(){
        return getName() + ", " + getCity().getFullName();
    }
}
