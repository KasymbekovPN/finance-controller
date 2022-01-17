package kpn.financecontroller.data.domains.building;

import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.entities.building.BuildingEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Building {
    private Long id;
    private String name;
    private Street street;

    public Building(BuildingEntity entity) {
        id = entity.getId();
        name = entity.getName();
        street = entity.getStreetEntity() != null ? new Street(entity.getStreetEntity()) : null;
    }

    public String getFullName(){
        return getName() + ", " + street.getFullName();
    }
}
