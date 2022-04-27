package kpn.financecontroller.data.domains.address;

import kpn.financecontroller.data.domains.AbstractDomain;
import kpn.financecontroller.data.entities.address.AddressEntity;
import kpn.financecontroller.data.domains.street.Street;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Address extends AbstractDomain {
    private String name;
    private Street street;

    public Address(AddressEntity entity) {
        id = entity.getId();
        name = entity.getName();
        street = entity.getStreetEntity() != null ? new Street(entity.getStreetEntity()) : null;
    }

    @Override
    public String getInfo() {
        return getName() + ", " + street.getInfo();
    }
}
