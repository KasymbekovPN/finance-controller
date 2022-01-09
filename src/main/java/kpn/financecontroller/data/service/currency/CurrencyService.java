package kpn.financecontroller.data.service.currency;

import kpn.financecontroller.data.domain.currency.Currency;
import kpn.financecontroller.data.entities.currency.CurrencyEntity;
import kpn.financecontroller.data.repo.currency.CurrencyRepo;
import kpn.financecontroller.data.service.Loader;
import kpn.financecontroller.data.service.Saver;
import kpn.financecontroller.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO: 08.01.2022 add interface + add generic Service + creation in ...Config
@Service
public class CurrencyService {

    private final CurrencyRepo currencyRepo;

    private final CurrencySaver saver;
    private final CurrencyLoader loader;

    @Autowired
    public CurrencyService(CurrencyRepo currencyRepo, CurrencySaver saver, CurrencyLoader loader) {
        this.currencyRepo = currencyRepo;
        this.saver = saver;
        this.loader = loader;
    }

    // TODO: 08.01.2022 to interface
    public Saver<Currency, CurrencyEntity> saver(){
        return saver;
    }

    public Loader<Currency, CurrencyEntity, Long> loader(){
        return loader;
    };

    // TODO: 08.01.2022 del
    public Result<Currency> saveOld(CurrencyEntity currencyEntity) {
        try{
            currencyRepo.save(currencyEntity);
            return Result.<Currency>builder()
                    .success(true)
                    .value(new Currency(currencyEntity))
                    .build();
        } catch (Throwable ex){
            // TODO: 05.01.2022 extend definition
            return Result.<Currency>builder()
                    .success(false)
                    .code("service.currency.save.fail")
                    .build();
        }
    }

    public Result<Currency> loadByIdOld(Long id) {
        Optional<CurrencyEntity> maybeEntity = currencyRepo.findById(id);
        if (maybeEntity.isPresent()){
            return Result.<Currency>builder()
                    .success(true)
                    .value(new Currency(maybeEntity.get()))
                    .build();
        }
        return Result.<Currency>builder()
                .success(false)
                .code("service.currency.loadById.noOne")
                .arg(id)
                .build();
    }

    public Result<List<Currency>> findAllOld(){
        List<CurrencyEntity> entities = currencyRepo.findAll();
        List<Currency> currencies = entities.stream().map(Currency::new).collect(Collectors.toList());
        return Result.<List<Currency>>builder().success(true).value(currencies).build();
    }

    public void deleteById(Long id) {
        currencyRepo.deleteById(id);
    }

    public Result<List<Currency>> search(String filter) {
        if (filter == null || filter.isEmpty()){
            return findAllOld();
        }

        List<CurrencyEntity> entities = currencyRepo.search(filter);
        List<Currency> currencies = entities.stream().map(Currency::new).collect(Collectors.toList());
        return Result.<List<Currency>>builder().success(true).value(currencies).build();
    }
}
