package kpn.financecontroller.data.services.loaders.country;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.services.DTOServiceException;
import kpn.financecontroller.data.services.loaders.AbstractLoader;
import kpn.financecontroller.data.repos.country.CountryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
final public class CountryLoader extends AbstractLoader<Country, CountryEntity, Long> {

    private final CountryRepo repo;

    @Autowired
    public CountryLoader(CountryRepo repo) {
        this.repo = repo;
    }

    @Override
    protected List<CountryEntity> loadAll() throws DTOServiceException {
        try{
            return repo.findAll();
        } catch (Throwable t){
            throw new DTOServiceException("loader.loadAll.fail");
        }
    }

    @Override
    protected Country convertEntityToDomain(CountryEntity entity) {
        return new Country(entity);
    }

    @Override
    protected List<Country> convertEntitiesToDomains(List<CountryEntity> entities) {
        return entities.stream().map(Country::new).collect(Collectors.toList());
    }
}
