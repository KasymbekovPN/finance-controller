package kpn.financecontroller.data.domains.place;

import kpn.financecontroller.data.domains.building.Building;
import kpn.financecontroller.data.entities.place.PlaceEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Place {
    private Long id;
    private String name;
    private boolean online;
    private Building building;

    public Place(PlaceEntity placeEntity) {
        id = placeEntity.getId();
        name = placeEntity.getName();
        online = placeEntity.isOnline();
        building = placeEntity.getBuildingEntity() != null ? new Building(placeEntity.getBuildingEntity()) : null;
    }
}
