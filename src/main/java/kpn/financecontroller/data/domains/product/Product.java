package kpn.financecontroller.data.domains.product;

import kpn.financecontroller.data.entities.product.ProductEntity;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.lib.domain.AbstractDomain;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Product extends AbstractDomain<Long> {
    private String name;
    private Set<Tag> tags;

    public Product(ProductEntity entity) {
        setId(entity.getId());
        setName(entity.getName());
        setTags(entity.getTagEntities().stream().map(Tag::new).collect(Collectors.toSet()));
    }

    @Override
    public String getInfo() {
        StringBuilder result = new StringBuilder();
        if (tags != null) {
            String delimiter = "";
            for (Tag tag : tags) {
                result.append(delimiter).append(tag.getInfo());
                delimiter = ", ";
            }
        }
        return result.toString();
    }
}
