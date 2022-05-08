package kpn.financecontroller.data.domains.place;

import kpn.financecontroller.data.domains.AbstractDomain;
import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.data.entities.place.PlaceEntity;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Place extends AbstractDomain {
    private String name;
    private boolean online;
    private Address address;

    public Place(PlaceEntity placeEntity) {
        id = placeEntity.getId();
        name = placeEntity.getName();
        online = placeEntity.isOnline();
        address = placeEntity.getAddressEntity() != null ? new Address(placeEntity.getAddressEntity()) : null;
    }

    @Override
    public String getInfo() {
        return getName();
    }
}
