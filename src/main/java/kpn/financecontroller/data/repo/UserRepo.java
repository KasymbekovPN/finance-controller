package kpn.financecontroller.data.repo;

import kpn.financecontroller.data.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
}
