package kpn.financecontroller.data.services.dto.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domain.Country;
import kpn.lib.aspect.deleting.DeletingAspect;
import kpn.lib.aspect.loading.LoadingAspect;
import kpn.lib.aspect.predicate.PredicateAspect;
import kpn.lib.aspect.saving.SavingAspect;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class CountryDtoDecorator implements Service<Long, Country, Predicate, Result<List<Country>>> {
    private final Service<Long, Country, Predicate, Result<List<Country>>> service;

    @Override
    public SavingAspect<Country, Result<List<Country>>> saver() {
        return service.saver();
    }

    @Override
    public LoadingAspect<Long, Result<List<Country>>> loader() {
        return service.loader();
    }

    @Override
    public DeletingAspect<Long, Result<List<Country>>> deleter() {
        return service.deleter();
    }

    @Override
    public PredicateAspect<Predicate, Result<List<Country>>> executor() {
        return service.executor();
    }
}
