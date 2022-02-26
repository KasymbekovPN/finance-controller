package kpn.financecontroller.data.repos.country;

import kpn.financecontroller.data.entities.country.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CountryRepo extends JpaRepository<CountryEntity, Long> {
    List<CountryEntity> findByName(String name);

    // TODO: 26.02.2022 del ???
//    @Query("select c from country c where c.name like concat('%', :filter, '%')")
//    List<CountryEntity> search(@Param("filter") String filter);
}
