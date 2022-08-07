package kpn.financecontroller.data.entity;

import kpn.financecontroller.data.domain.Address;
import kpn.lib.entity.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "addresses")
public final class AddressEntity extends AbstractEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(max = 64)
    private String name;

    @ManyToOne
    @JoinColumn(name = "street_id")
    private StreetEntity streetEntity;

    public AddressEntity(Address address) {
        setId(address.getId());
        setName(address.getName());
        setStreetEntity(new StreetEntity(address.getStreet()));
    }
}
