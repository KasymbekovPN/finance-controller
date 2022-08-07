package kpn.financecontroller.data.repo;

import kpn.financecontroller.data.entity.StreetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface StreetRepo extends JpaRepository<StreetEntity, Long>, QuerydslPredicateExecutor<StreetEntity> {
}
