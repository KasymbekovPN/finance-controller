package kpn.financecontroller.data.repos.region;

import kpn.financecontroller.data.entities.region.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RegionRepo extends JpaRepository<RegionEntity, Long>, QuerydslPredicateExecutor<RegionEntity> {
}
