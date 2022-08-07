package kpn.financecontroller.data.repo;

import kpn.financecontroller.data.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RegionRepo extends JpaRepository<RegionEntity, Long>, QuerydslPredicateExecutor<RegionEntity> {
}
