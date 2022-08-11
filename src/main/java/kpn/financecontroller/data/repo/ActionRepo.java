package kpn.financecontroller.data.repo;

import kpn.financecontroller.data.entity.ActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ActionRepo extends JpaRepository<ActionEntity, Long>, QuerydslPredicateExecutor<ActionEntity> {
}
