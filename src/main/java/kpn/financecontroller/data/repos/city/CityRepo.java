package kpn.financecontroller.data.repos.city;

import kpn.financecontroller.data.entities.city.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CityRepo extends JpaRepository<CityEntity, Long>, QuerydslPredicateExecutor<CityEntity> {
}
