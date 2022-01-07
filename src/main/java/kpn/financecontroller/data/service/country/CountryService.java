package kpn.financecontroller.data.service.country;

import kpn.financecontroller.data.domain.country.Country;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.repo.country.CountryRepo;
import kpn.financecontroller.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryService {

    private final CountryRepo countryRepo;

    @Autowired
    public CountryService(CountryRepo countryRepo) {
        this.countryRepo = countryRepo;
    }

    public Result<Country> save(CountryEntity entity) {
        try{
            countryRepo.save(entity);
            return Result.<Country>builder()
                    .success(true)
                    .value(new Country(entity))
                    .build();
        } catch (Throwable ex){
            // TODO: 05.01.2022 extend definition
            return Result.<Country>builder()
                    .success(false)
                    .code("service.country.save.fail")
                    .build();
        }
    }

    // TODO: 07.01.2022 rename to loadAll
    public Result<List<Country>> findAll() {
        List<CountryEntity> entities = countryRepo.findAll();
        List<Country> currencies = entities.stream().map(Country::new).collect(Collectors.toList());
        return Result.<List<Country>>builder().success(true).value(currencies).build();
    }

    public void deleteAll() {
        countryRepo.deleteAll();
    }

    public Result<Country> loadById(Long id) {
        Optional<CountryEntity> maybeEntity = countryRepo.findById(id);
        if (maybeEntity.isPresent()){
            return Result.<Country>builder()
                    .success(true)
                    .value(new Country(maybeEntity.get()))
                    .build();
        }
        return Result.<Country>builder()
                .success(false)
                .code("service.country.loadById.noOne")
                .arg(id)
                .build();
    }

    public void deleteById(Long id) {
        countryRepo.deleteById(id);
    }

    public Result<List<Country>> search(String filter) {
        if (filter == null || filter.isEmpty()){
            return findAll();
        }

        List<CountryEntity> entities = countryRepo.search(filter);
        List<Country> currencies = entities.stream().map(Country::new).collect(Collectors.toList());
        return Result.<List<Country>>builder().success(true).value(currencies).build();
    }
}
