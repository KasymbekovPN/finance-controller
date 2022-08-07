package kpn.financecontroller.data.repo;

import kpn.financecontroller.data.entity.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SellerRepo extends JpaRepository<SellerEntity, Long>, QuerydslPredicateExecutor<SellerEntity> {
}
