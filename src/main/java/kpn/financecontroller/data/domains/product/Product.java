package kpn.financecontroller.data.domains.product;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.product.ProductEntity;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Product {
    private Long id;
    private String name;
    private Set<Tag> tags;

    public Product(ProductEntity entity) {
        id = entity.getId();
        name = entity.getName();
        tags = entity.getTagEntities().stream().map(Tag::new).collect(Collectors.toSet());
    }
    
    public String getTagsAsStr() {
        StringBuilder result = new StringBuilder();
        String delimiter = "";

        for (Tag tag : tags) {
            result.append(delimiter).append(tag.toString());
            delimiter = ", ";
        }

        return result.toString();
    }
}
