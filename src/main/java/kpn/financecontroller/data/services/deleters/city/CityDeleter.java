package kpn.financecontroller.data.services.deleters.city;

import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.repos.city.CityRepo;
import kpn.financecontroller.data.services.DTOServiceException;
import kpn.financecontroller.data.services.deleters.AbstractDeleter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
final public class CityDeleter extends AbstractDeleter<City, CityEntity, Long> {

    private final CityRepo repo;

    @Autowired
    public CityDeleter(CityRepo repo) {
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
