package kpn.financecontroller.data.repos.address;

import kpn.financecontroller.data.entities.address.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AddressRepo extends JpaRepository<AddressEntity, Long>, QuerydslPredicateExecutor<AddressEntity> {
}
