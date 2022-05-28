package kpn.financecontroller.data.repos.country;

import kpn.financecontroller.data.entities.country.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CountryRepo extends JpaRepository<CountryEntity, Long>, QuerydslPredicateExecutor<CountryEntity> {
}
