package kpn.financecontroller.data.repo.currency;

import kpn.financecontroller.data.entities.currency.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrencyRepo extends JpaRepository<CurrencyEntity, Long> {
    List<CurrencyEntity> findByCode(String code);
}
