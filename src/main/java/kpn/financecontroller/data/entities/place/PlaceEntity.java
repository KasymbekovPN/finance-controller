package kpn.financecontroller.data.entities.place;

import kpn.financecontroller.data.domains.place.Place;
import kpn.financecontroller.data.entities.AbstractEntity;
import kpn.financecontroller.data.entities.building.BuildingEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "places")
public class PlaceEntity extends AbstractEntity {

    @NotEmpty
    @Size(max = 64)
    private String name;

    private boolean online;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private BuildingEntity buildingEntity;

    public PlaceEntity(Place place) {
        id = place.getId();
        name = place.getName();
        online = place.isOnline();
        buildingEntity = place.getBuilding() != null ? new BuildingEntity(place.getBuilding()) : null;
    }
}
