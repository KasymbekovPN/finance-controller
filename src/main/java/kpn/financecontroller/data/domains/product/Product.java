package kpn.financecontroller.data.domains.product;

import kpn.financecontroller.data.domains.AbstractDomain;
import kpn.financecontroller.data.entities.product.ProductEntity;
import kpn.financecontroller.data.domains.tag.Tag;
import lombok.*;

import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Product extends AbstractDomain {

    private static final Map<String, Function<Product, String>> GETTERS = Map.of(
            "id",
            product -> {return product.getId() != null ? product.getId().toString() : DEFAULT_GETTING_RESULT;},
            "name",
            product -> {
                String name = product.getName();
                return name != null && !name.isEmpty() ? name : DEFAULT_GETTING_RESULT;
            },
            "tags",
            Product::getInfo
    );

    private String name;
    private Set<Tag> tags;

    public Product(ProductEntity entity) {
        id = entity.getId();
        name = entity.getName();
        tags = entity.getTagEntities().stream().map(Tag::new).collect(Collectors.toSet());
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

    @Override
    public String get(Queue<String> path) {
        if (path.size() == 1){
            String key = path.poll();
            if (GETTERS.containsKey(key)){
                return GETTERS.get(key).apply(this);
            }
        }
        return DEFAULT_GETTING_RESULT;
    }
}
