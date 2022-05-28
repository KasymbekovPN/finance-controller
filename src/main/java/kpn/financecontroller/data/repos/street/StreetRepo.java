package kpn.financecontroller.data.repos.street;

import kpn.financecontroller.data.entities.street.StreetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface StreetRepo extends JpaRepository<StreetEntity, Long>, QuerydslPredicateExecutor<StreetEntity> {
}
