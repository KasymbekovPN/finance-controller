package kpn.financecontroller.data.entities.address;

import kpn.financecontroller.data.domains.address.Address;
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
@Entity(name = "addresses")
public class AddressEntity extends AbstractEntity {
    @NotEmpty
    @Size(max = 64)
    private String name;

    @ManyToOne
    @JoinColumn(name = "street_id")
    private StreetEntity streetEntity;

    public AddressEntity(Address address) {
        id = address.getId();
        name = address.getName();
        streetEntity = new StreetEntity(address.getStreet());
    }
}
