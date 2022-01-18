package kpn.financecontroller.data.domains.product;

import kpn.financecontroller.data.entities.product.ProductEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Product {
    private Long id;
    private String name;

    public Product(ProductEntity entity) {
        id = entity.getId();
        name = entity.getName();
    }
}
