package kpn.financecontroller.data.repos.currency;

import kpn.financecontroller.data.entities.currency.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrencyRepo extends JpaRepository<CurrencyEntity, Long> {
    List<CurrencyEntity> findByCode(String code);

    // TODO: 09.01.2022 how it use
//    @Query("select c from currency c where c.code like concat('%', :filter, '%')")
//    List<CurrencyEntity> search(@Param("filter") String filter);
}
