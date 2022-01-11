package kpn.financecontroller.data.repos.region;

import kpn.financecontroller.data.entities.region.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepo extends JpaRepository<RegionEntity, Long> {
}
