package kpn.financecontroller.data.domains.product;

import kpn.financecontroller.data.domains.AbstractDomain;
import kpn.financecontroller.data.entities.product.ProductEntity;
import kpn.financecontroller.data.domains.tag.Tag;
import lombok.*;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Product extends AbstractDomain {

    private static final Map<String, Function<GetterArg, String>> GETTERS = Map.of(
            "id",
            arg -> {
                Long id = arg.getDomain().getId();
                return arg.getPath().isEmpty() && id != null
                        ? id.toString()
                        : DEFAULT_GETTING_RESULT;
            },
            "name",
            arg -> {
                Product domain = (Product) arg.getDomain();
                String name = domain.getName();
                return arg.getPath().isEmpty() && name != null && !name.isEmpty()
                        ? name
                        : DEFAULT_GETTING_RESULT;
            },
            "tags",
            arg -> {
                Product domain = (Product) arg.getDomain();
                return arg.getPath().isEmpty()
                        ? domain.getInfo()
                        : DEFAULT_GETTING_RESULT;
            }
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
    protected Map<String, Function<GetterArg, String>> takeGetters() {
        return GETTERS;
    }
}
