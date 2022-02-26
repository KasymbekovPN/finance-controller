package kpn.financecontroller.data.repos.address;

import kpn.financecontroller.data.entities.address.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<AddressEntity, Long> {
}
