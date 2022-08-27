package kpn.financecontroller.data.services.dto.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domain.City;
import kpn.lib.aspect.deleting.DeletingAspect;
import kpn.lib.aspect.loading.LoadingAspect;
import kpn.lib.aspect.predicate.PredicateAspect;
import kpn.lib.aspect.saving.SavingAspect;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class CityDtoDecorator implements Service<Long, City, Predicate, Result<List<City>>> {
    private final Service<Long, City, Predicate, Result<List<City>>> service;

    @Override
    public SavingAspect<City, Result<List<City>>> saver() {
        return service.saver();
    }

    @Override
    public LoadingAspect<Long, Result<List<City>>> loader() {
        return service.loader();
    }

    @Override
    public DeletingAspect<Long, Result<List<City>>> deleter() {
        return service.deleter();
    }

    @Override
    public PredicateAspect<Predicate, Result<List<City>>> executor() {
        return service.executor();
    }
}
