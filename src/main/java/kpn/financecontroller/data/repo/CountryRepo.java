package kpn.financecontroller.data.repo;

import kpn.financecontroller.data.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CountryRepo extends JpaRepository<CountryEntity, Long>, QuerydslPredicateExecutor<CountryEntity> {
}
