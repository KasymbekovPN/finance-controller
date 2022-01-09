package kpn.financecontroller.data.service.currency;

import kpn.financecontroller.data.domain.currency.Currency;
import kpn.financecontroller.data.entities.currency.CurrencyEntity;
import kpn.financecontroller.data.repo.currency.CurrencyRepo;
import kpn.financecontroller.data.service.AbstractLoader;
import kpn.financecontroller.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
final public class CurrencyLoader extends AbstractLoader<Currency, CurrencyEntity, Long> {

    private final CurrencyRepo repo;

    @Autowired
    public CurrencyLoader(CurrencyRepo repo) {
        this.repo = repo;
    }

    @Override
    protected Optional<CurrencyEntity> loadById(Long id) {
        return repo.findById(id);
    }

    @Override
    protected List<CurrencyEntity> loadAll() {
        return repo.findAll();
    }

    @Override
    protected Result.Builder<Currency> getResultBuilder() {
        return Result.<Currency>builder();
    }

    @Override
    protected Result.Builder<List<Currency>> getListResultBuilder() {
        return Result.<List<Currency>>builder();
    }

    @Override
    protected Currency convertEntityToDomain(CurrencyEntity entity) {
        return new Currency(entity);
    }

    @Override
    protected List<Currency> convertEntitiesToDomains(List<CurrencyEntity> entity) {
        return entity.stream().map(Currency::new).collect(Collectors.toList());
    }
}
