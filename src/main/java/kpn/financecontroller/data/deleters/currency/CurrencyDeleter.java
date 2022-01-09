package kpn.financecontroller.data.deleters.currency;

import kpn.financecontroller.data.domains.currency.Currency;
import kpn.financecontroller.data.entities.currency.CurrencyEntity;
import kpn.financecontroller.data.repos.currency.CurrencyRepo;
import kpn.financecontroller.data.deleters.AbstractDeleter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
final public class CurrencyDeleter extends AbstractDeleter<Currency, CurrencyEntity, Long> {

    private final CurrencyRepo repo;

    @Autowired
    public CurrencyDeleter(CurrencyRepo repo) {
        this.repo = repo;
    }

    @Override
    protected void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    protected void deleteAll() {
        repo.deleteAll();
    }
}
