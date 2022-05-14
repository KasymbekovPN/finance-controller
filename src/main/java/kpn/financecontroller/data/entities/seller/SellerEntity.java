package kpn.financecontroller.data.entities.seller;

import kpn.financecontroller.data.domains.seller.Seller;
import kpn.financecontroller.data.entities.AbstractEntity;
import kpn.financecontroller.data.entities.address.AddressEntity;
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
@Entity(name = "sellers")
public class SellerEntity extends AbstractEntity {

    @NotEmpty
    @Size(max = 64)
    private String name;
    @Size(max = 1024)
    private String url;
    @Size(max = 1024)
    private String description;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private AddressEntity addressEntity;

    public SellerEntity(Seller seller) {
        id = seller.getId();
        name = seller.getName();
        url = seller.getUrl();
        description = seller.getDescription();
        addressEntity = seller.getAddress() != null ? new AddressEntity(seller.getAddress()) : null;
    }
}
