package kpn.financecontroller.data.repos.seller;

import kpn.financecontroller.data.entities.seller.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SellerRepo extends JpaRepository<SellerEntity, Long>, QuerydslPredicateExecutor<SellerEntity> {
}
