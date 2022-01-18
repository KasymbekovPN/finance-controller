package kpn.financecontroller.data.repos.product;

import kpn.financecontroller.data.entities.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<ProductEntity, Long> {
}
