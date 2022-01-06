package kpn.financecontroller.data.repo.currency;

import kpn.financecontroller.data.entities.currency.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CurrencyRepo extends JpaRepository<CurrencyEntity, Long> {
    List<CurrencyEntity> findByCode(String code);

    @Query("select c from currency c where c.code like concat('%', :filter, '%')")
    List<CurrencyEntity> search(@Param("filter") String filter);
}
