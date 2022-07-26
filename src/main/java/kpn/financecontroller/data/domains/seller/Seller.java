package kpn.financecontroller.data.domains.seller;

import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.data.entities.seller.SellerEntity;
import kpn.lib.domain.AbstractDomain;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Seller extends AbstractDomain<Long> {
    private String name;
    private String url;
    private String description;
    private Address address;

    public Seller(SellerEntity sellerEntity) {
        setId(sellerEntity.getId());
        setName(sellerEntity.getName());
        setUrl(sellerEntity.getUrl());
        setDescription(sellerEntity.getDescription());
        setAddress(sellerEntity.getAddressEntity() != null ? new Address(sellerEntity.getAddressEntity()) : null);
    }

    @Override
    public String getInfo() {
        return getName();
    }
}
