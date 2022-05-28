package kpn.financecontroller.data.repos.product;

import kpn.financecontroller.data.entities.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductRepo extends JpaRepository<ProductEntity, Long>, QuerydslPredicateExecutor<ProductEntity> {
}
