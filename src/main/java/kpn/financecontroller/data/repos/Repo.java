package kpn.financecontroller.data.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface Repo<ENTITY> extends JpaRepository<ENTITY, Long>, QuerydslPredicateExecutor<ENTITY> {
}
