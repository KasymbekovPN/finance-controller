package kpn.financecontroller.data.repo;

import kpn.financecontroller.data.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductRepo extends JpaRepository<ProductEntity, Long>, QuerydslPredicateExecutor<ProductEntity> {
}
