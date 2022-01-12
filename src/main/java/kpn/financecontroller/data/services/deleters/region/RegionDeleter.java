package kpn.financecontroller.data.services.deleters.region;

import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.repos.region.RegionRepo;
import kpn.financecontroller.data.services.DTOServiceException;
import kpn.financecontroller.data.services.deleters.AbstractDeleter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
final public class RegionDeleter extends AbstractDeleter<Region, RegionEntity, Long> {

    private final RegionRepo repo;

    @Autowired
    public RegionDeleter(RegionRepo repo) {
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
