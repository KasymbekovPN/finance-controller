package kpn.financecontroller.data.service.currency;

import kpn.financecontroller.data.domain.currency.Currency;
import kpn.financecontroller.data.entities.currency.CurrencyEntity;
import kpn.financecontroller.data.repo.currency.CurrencyRepo;
import kpn.financecontroller.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrencyService {

    private final CurrencyRepo currencyRepo;

    @Autowired
    public CurrencyService(CurrencyRepo currencyRepo) {
        this.currencyRepo = currencyRepo;
    }

    public Result<Currency> save(CurrencyEntity currencyEntity) {
        try{
            currencyRepo.save(currencyEntity);
            return Result.<Currency>builder()
                    .success(true)
                    .value(new Currency(currencyEntity.getCode()))
                    .build();
        } catch (Throwable ex){
            // TODO: 05.01.2022 extend definition
            return Result.<Currency>builder()
                    .success(false)
                    .code("service.currency.save.fail")
                    .build();
        }
    }

    public Result<Currency> loadById(Long id) {
        Optional<CurrencyEntity> maybeEntity = currencyRepo.findById(id);
        if (maybeEntity.isPresent()){
            return Result.<Currency>builder()
                    .success(true)
                    .value(new Currency(maybeEntity.get().getCode()))
                    .build();
        }
        return Result.<Currency>builder()
                .success(false)
                .code("service.currency.loadById.noOne")
                .arg(id)
                .build();
    }

    public void deleteById(Long id) {
        currencyRepo.deleteById(id);
    }
}
