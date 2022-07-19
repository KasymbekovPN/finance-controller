package kpn.financecontroller.data.entities.tag;

import kpn.financecontroller.data.entities.product.ProductEntity;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.lib.entity.AbstractEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "tags")
public final class TagEntity extends AbstractEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(max = 64)
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "tagEntities")
    private Set<ProductEntity> productEntities;

    public TagEntity(Tag tag) {
        setId(tag.getId());
        setName(tag.getName());
    }
}
