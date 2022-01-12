package kpn.financecontroller.data.services.savers.region;

import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.repos.region.RegionRepo;
import kpn.financecontroller.data.services.DTOServiceException;
import kpn.financecontroller.data.services.savers.AbstractSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
final public class RegionSaver extends AbstractSaver<Region, RegionEntity> {

    private final RegionRepo repo;

    @Autowired
    public RegionSaver(RegionRepo repo) {
        this.repo = repo;
    }

    @Override
    protected RegionEntity saveImpl(RegionEntity entity) throws DTOServiceException {
        try{
            return repo.save(entity);
        } catch (Throwable t){
            throw new DTOServiceException("saver.saveImpl.fail");
        }
    }

    @Override
    protected Region convertEntityToDomain(RegionEntity entity) {
        return new Region(entity);
    }
}
