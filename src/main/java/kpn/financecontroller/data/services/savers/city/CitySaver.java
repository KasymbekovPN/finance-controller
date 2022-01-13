package kpn.financecontroller.data.services.savers.city;

import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.repos.city.CityRepo;
import kpn.financecontroller.data.services.DTOServiceException;
import kpn.financecontroller.data.services.savers.AbstractSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
final public class CitySaver extends AbstractSaver<City, CityEntity> {

    private final CityRepo repo;

    @Autowired
    public CitySaver(CityRepo repo) {
        this.repo = repo;
    }

    @Override
    protected CityEntity saveImpl(CityEntity entity) throws DTOServiceException {
        try{
            return repo.save(entity);
        } catch (Throwable t){
            throw new DTOServiceException("saver.saveImpl.fail");
        }
    }

    @Override
    protected City convertEntityToDomain(CityEntity entity) {
        return new City(entity);
    }
}
