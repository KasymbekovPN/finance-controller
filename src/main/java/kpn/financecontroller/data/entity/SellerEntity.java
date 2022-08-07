package kpn.financecontroller.data.entity;

import kpn.financecontroller.data.domain.Seller;
import kpn.financecontroller.data.entity.AddressEntity;
import kpn.lib.entity.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "sellers")
public final class SellerEntity extends AbstractEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
        setId(seller.getId());
        setName(seller.getName());
        setUrl(seller.getUrl());
        setDescription(seller.getDescription());
        setAddressEntity(seller.getAddress() != null ? new AddressEntity(seller.getAddress()) : null);
    }
}
