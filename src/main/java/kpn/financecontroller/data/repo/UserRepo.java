package kpn.financecontroller.data.repo;

import kpn.financecontroller.data.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByUsername(String username);
}
