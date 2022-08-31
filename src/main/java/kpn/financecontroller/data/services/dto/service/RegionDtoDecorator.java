package kpn.financecontroller.data.services.dto.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domain.Region;
import kpn.lib.aspect.deleting.DeletingAspect;
import kpn.lib.aspect.loading.LoadingAspect;
import kpn.lib.aspect.predicate.PredicateAspect;
import kpn.lib.aspect.saving.SavingAspect;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class RegionDtoDecorator implements Service<Long, Region, Predicate, Result<List<Region>>> {
    private final Service<Long, Region, Predicate, Result<List<Region>>> service;

    @Override
    public SavingAspect<Region, Result<List<Region>>> saver() {
        return service.saver();
    }

    @Override
    public LoadingAspect<Long, Result<List<Region>>> loader() {
        return service.loader();
    }

    @Override
    public DeletingAspect<Long, Result<List<Region>>> deleter() {
        return service.deleter();
    }

    @Override
    public PredicateAspect<Predicate, Result<List<Region>>> executor() {
        return service.executor();
    }
}
