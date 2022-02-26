package kpn.financecontroller.data.entities.product;

import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.entities.AbstractEntity;
import kpn.financecontroller.data.entities.tag.TagEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "products")
public class ProductEntity extends AbstractEntity {
    @NotEmpty
    @Column(unique = true)
    @Size(max = 256)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "product_tags",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<TagEntity> tagEntities;

    public ProductEntity(Product value) {
        id = value.getId();
        name = value.getName();
        tagEntities = value.getTags().stream().map(TagEntity::new).collect(Collectors.toSet());
    }
}
