package kpn.financecontroller.data.repos.country;

import kpn.financecontroller.data.entities.country.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepo extends JpaRepository<CountryEntity, Long> {
    List<CountryEntity> findByName(String name);
}
