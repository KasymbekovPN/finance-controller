package kpn.financecontroller.data.savers.currency;

import kpn.financecontroller.data.domains.currency.Currency;
import kpn.financecontroller.data.entities.currency.CurrencyEntity;
import kpn.financecontroller.data.repos.currency.CurrencyRepo;
import kpn.financecontroller.data.savers.AbstractSaver;
import kpn.financecontroller.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
final public class CurrencySaver extends AbstractSaver<Currency, CurrencyEntity> {

    private final CurrencyRepo repo;

    @Autowired
    public CurrencySaver(CurrencyRepo repo) {
        this.repo = repo;
    }

    @Override
    protected CurrencyEntity saveImpl(CurrencyEntity entity) {
        return repo.save(entity);
    }

    @Override
    protected Currency convertEntityToDomain(CurrencyEntity entity) {
        return new Currency(entity);
    }

    @Override
    protected Result.Builder<Currency> getResultBuilder() {
        return Result.<Currency>builder();
    }
}
