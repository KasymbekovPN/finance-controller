package kpn.financecontroller.data.domains.address;

import kpn.financecontroller.data.entities.address.AddressEntity;
import kpn.financecontroller.data.domains.street.Street;
import kpn.lib.domain.AbstractDomain;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Address extends AbstractDomain<Long> {
    private String name;
    private Street street;

    public Address(AddressEntity entity) {
        setId(entity.getId());
        setName(entity.getName());
        setStreet(new Street(entity.getStreetEntity()));
    }

    @Override
    public String getInfo() {
        return getName() + ", " + street.getInfo();
    }
}
