package kpn.financecontroller.data.repo;

import kpn.financecontroller.data.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CityRepo extends JpaRepository<CityEntity, Long>, QuerydslPredicateExecutor<CityEntity> {
}
