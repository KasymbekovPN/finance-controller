package kpn.financecontroller.data.repo;

import kpn.financecontroller.data.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AddressRepo extends JpaRepository<AddressEntity, Long>, QuerydslPredicateExecutor<AddressEntity> {
}
