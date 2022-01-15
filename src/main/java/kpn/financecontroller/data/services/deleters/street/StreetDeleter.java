package kpn.financecontroller.data.services.deleters.street;

import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.repos.city.CityRepo;
import kpn.financecontroller.data.repos.street.StreetRepo;
import kpn.financecontroller.data.services.DTOServiceException;
import kpn.financecontroller.data.services.deleters.AbstractDeleter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
final public class StreetDeleter extends AbstractDeleter<Street, StreetEntity, Long> {

    private final StreetRepo repo;

    @Autowired
    public StreetDeleter(StreetRepo repo) {
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
