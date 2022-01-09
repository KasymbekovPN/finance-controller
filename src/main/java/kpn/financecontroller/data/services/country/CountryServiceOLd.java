package kpn.financecontroller.data.services.country;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.repos.country.CountryRepo;
import kpn.financecontroller.data.services.AbstractDTOServiceOLd;
import kpn.financecontroller.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryServiceOLd extends AbstractDTOServiceOLd<Country, CountryEntity> {

    private final CountryRepo repo;

    @Autowired
    public CountryServiceOLd(CountryRepo repo) {
        this.repo = repo;
    }

    public Result<Country> save(CountryEntity entity) {
        return saveImpl(entity, repo);
    }

    public Result<List<Country>> loadAll() {
        return loadAllImpl(repo);
    }

    public void deleteAll() {
        deleteAllImpl(repo);
    }

    public Result<Country> loadById(Long id) {
        return loadByIdImpl(id, repo);
    }

    public void deleteById(Long id) {
        deleteByIdImpl(id, repo);
    }

    public Result<List<Country>> search(String filter) {
        return searchImpl(filter, repo);
    }

    @Override
    protected Result.Builder<Country> getResultBuilder() {
        return Result.<Country>builder();
    }

    @Override
    protected Result.Builder<List<Country>> getListResultBuilder() {
        return Result.<List<Country>>builder();
    }

    @Override
    protected Country convertEntityToDomain(CountryEntity entity) {
        return new Country(entity);
    }

    @Override
    protected List<Country> convertEntitiesToDomains(List<CountryEntity> entities) {
        return entities.stream().map(Country::new).collect(Collectors.toList());
    }

    @Override
    protected List<CountryEntity> getSearchedEntities(String filter) {
        return repo.search(filter);
    }
}
