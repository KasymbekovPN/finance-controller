package kpn.financecontroller.data.services.loaders.city;

import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.repos.city.CityRepo;
import kpn.financecontroller.data.services.DTOServiceException;
import kpn.financecontroller.data.services.loaders.AbstractLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
final public class CityLoader extends AbstractLoader<City, CityEntity, Long> {

    private final CityRepo repo;

    @Autowired
    public CityLoader(CityRepo repo) {
        this.repo = repo;
    }

    @Override
    protected List<CityEntity> loadAll() throws DTOServiceException {
        try{
            return repo.findAll();
        } catch (Throwable t){
            throw new DTOServiceException("loader.loadAll.fail");
        }
    }

    @Override
    protected City convertEntityToDomain(CityEntity entity) {
        return new City(entity);
    }

    @Override
    protected List<City> convertEntitiesToDomains(List<CityEntity> entities) {
        return entities.stream().map(City::new).collect(Collectors.toList());
    }
}
