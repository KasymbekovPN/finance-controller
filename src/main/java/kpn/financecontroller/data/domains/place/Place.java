package kpn.financecontroller.data.domains.place;

import kpn.financecontroller.data.domains.address.Address;
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
    private Address address;

    public Place(PlaceEntity placeEntity) {
        id = placeEntity.getId();
        name = placeEntity.getName();
        online = placeEntity.isOnline();
        address = placeEntity.getAddressEntity() != null ? new Address(placeEntity.getAddressEntity()) : null;
    }
}
