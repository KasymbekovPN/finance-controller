package kpn.financecontroller.data.services.loaders.region;

import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.repos.region.RegionRepo;
import kpn.financecontroller.data.services.DTOServiceException;
import kpn.financecontroller.data.services.loaders.AbstractLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
final public class RegionLoader extends AbstractLoader<Region, RegionEntity, Long> {

    private final RegionRepo repo;

    @Autowired
    public RegionLoader(RegionRepo repo) {
        this.repo = repo;
    }

    @Override
    protected List<RegionEntity> loadAll() throws DTOServiceException {
        try{
            return repo.findAll();
        } catch (Throwable t){
            throw new DTOServiceException("loader.loadAll.fail");
        }
    }

    @Override
    protected Region convertEntityToDomain(RegionEntity entity) {
        return new Region(entity);
    }

    @Override
    protected List<Region> convertEntitiesToDomains(List<RegionEntity> entities) {
        return entities.stream().map(Region::new).collect(Collectors.toList());
    }
}
