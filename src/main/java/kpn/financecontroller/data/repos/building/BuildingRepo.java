package kpn.financecontroller.data.repos.building;

import kpn.financecontroller.data.entities.building.BuildingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepo extends JpaRepository<BuildingEntity, Long> {
}
