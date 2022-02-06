package kpn.financecontroller.data.entities.building;

import kpn.financecontroller.data.domains.building.Building;
import kpn.financecontroller.data.entities.AbstractEntity;
import kpn.financecontroller.data.entities.street.StreetEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "building")
public class BuildingEntity extends AbstractEntity {
    @NotEmpty
    @Size(max = 64)
    private String name;

    @ManyToOne
    @JoinColumn(name = "street_id")
    private StreetEntity streetEntity;

    public BuildingEntity(Building building) {
        id = building.getId();
        name = building.getName();
        streetEntity = new StreetEntity(building.getStreet());
    }
}
