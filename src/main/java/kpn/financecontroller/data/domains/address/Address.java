package kpn.financecontroller.data.domains.address;

import kpn.financecontroller.data.entities.address.AddressEntity;
import kpn.financecontroller.data.domains.street.Street;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Address {
    private Long id;
    private String name;
    private Street street;

    public Address(AddressEntity entity) {
        id = entity.getId();
        name = entity.getName();
        street = entity.getStreetEntity() != null ? new Street(entity.getStreetEntity()) : null;
    }

    public String getFullName(){
        return getName() + ", " + street.getFullName();
    }
}
