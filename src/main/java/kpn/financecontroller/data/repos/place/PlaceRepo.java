package kpn.financecontroller.data.repos.place;

import kpn.financecontroller.data.entities.place.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepo extends JpaRepository<PlaceEntity, Long> {
}
