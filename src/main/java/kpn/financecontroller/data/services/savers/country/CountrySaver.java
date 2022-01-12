package kpn.financecontroller.data.services.savers.country;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.repos.country.CountryRepo;
import kpn.financecontroller.data.services.DTOServiceException;
import kpn.financecontroller.data.services.savers.AbstractSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
final public class CountrySaver extends AbstractSaver<Country, CountryEntity> {

    private final CountryRepo repo;

    @Autowired
    public CountrySaver(CountryRepo repo) {
        this.repo = repo;
    }

    @Override
    protected CountryEntity saveImpl(CountryEntity entity) throws DTOServiceException {
        try{
            return repo.save(entity);
        } catch (Throwable t){
            throw new DTOServiceException("saver.saveImpl.fail");
        }
    }

    @Override
    protected Country convertEntityToDomain(CountryEntity entity) {
        return new Country(entity);
    }
}
