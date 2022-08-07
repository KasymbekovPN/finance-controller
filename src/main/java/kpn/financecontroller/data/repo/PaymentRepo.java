package kpn.financecontroller.data.repo;

import kpn.financecontroller.data.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PaymentRepo extends JpaRepository<PaymentEntity, Long>, QuerydslPredicateExecutor<PaymentEntity> {
}
