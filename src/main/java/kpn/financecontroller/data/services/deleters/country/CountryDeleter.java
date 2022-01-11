package kpn.financecontroller.data.services.deleters.country;

import kpn.financecontroller.data.services.DTOServiceException;
import kpn.financecontroller.data.services.deleters.AbstractDeleter;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.repos.country.CountryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
final public class CountryDeleter extends AbstractDeleter<Country, CountryEntity, Long> {

    private final CountryRepo repo;

    @Autowired
    public CountryDeleter(CountryRepo repo) {
        this.repo = repo;
    }

    @Override
    protected void deleteById(Long id) throws DTOServiceException {
        try{
            repo.deleteById(id);
        } catch (Throwable t){
            throw new DTOServiceException("deleter.deleteById.fail");
        }
    }

    @Override
    protected void deleteAll() throws DTOServiceException {
        try{
            repo.deleteAll();
        } catch (Throwable t){
            throw new DTOServiceException("deleter.deleteAll.fail");
        }
    }
}
