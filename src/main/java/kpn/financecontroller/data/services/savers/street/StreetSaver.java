package kpn.financecontroller.data.services.savers.street;

import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.repos.street.StreetRepo;
import kpn.financecontroller.data.services.DTOServiceException;
import kpn.financecontroller.data.services.savers.AbstractSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
final public class StreetSaver extends AbstractSaver<Street, StreetEntity> {

    private final StreetRepo repo;

    @Autowired
    public StreetSaver(StreetRepo repo) {
        this.repo = repo;
    }

    @Override
    protected StreetEntity saveImpl(StreetEntity entity) throws DTOServiceException {
        try{
            return repo.save(entity);
        } catch (Throwable t){
            throw new DTOServiceException("saver.saveImpl.fail");
        }
    }

    @Override
    protected Street convertEntityToDomain(StreetEntity entity) {
        return new Street(entity);
    }
}
