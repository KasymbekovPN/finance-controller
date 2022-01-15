package kpn.financecontroller.data.services.loaders.street;

import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.repos.street.StreetRepo;
import kpn.financecontroller.data.services.DTOServiceException;
import kpn.financecontroller.data.services.loaders.AbstractLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
final public class StreetLoader extends AbstractLoader<Street, StreetEntity, Long> {

    private final StreetRepo repo;

    @Autowired
    public StreetLoader(StreetRepo repo) {
        this.repo = repo;
    }

    @Override
    protected List<StreetEntity> loadAll() throws DTOServiceException {
        try{
            return repo.findAll();
        } catch (Throwable t){
            throw new DTOServiceException("loader.loadAll.fail");
        }
    }

    @Override
    protected Street convertEntityToDomain(StreetEntity entity) {
        return new Street(entity);
    }

    @Override
    protected List<Street> convertEntitiesToDomains(List<StreetEntity> entities) {
        return entities.stream().map(Street::new).collect(Collectors.toList());
    }
}
