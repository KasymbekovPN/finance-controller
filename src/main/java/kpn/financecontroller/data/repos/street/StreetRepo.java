package kpn.financecontroller.data.repos.street;

import kpn.financecontroller.data.entities.street.StreetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreetRepo extends JpaRepository<StreetEntity, Long> {
}
