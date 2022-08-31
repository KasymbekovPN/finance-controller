package kpn.financecontroller.data.services.dto.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domain.Street;
import kpn.lib.aspect.deleting.DeletingAspect;
import kpn.lib.aspect.loading.LoadingAspect;
import kpn.lib.aspect.predicate.PredicateAspect;
import kpn.lib.aspect.saving.SavingAspect;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class StreetDtoDecorator implements Service<Long, Street, Predicate, Result<List<Street>>> {
    private final Service<Long, Street, Predicate, Result<List<Street>>> service;

    @Override
    public SavingAspect<Street, Result<List<Street>>> saver() {
        return service.saver();
    }

    @Override
    public LoadingAspect<Long, Result<List<Street>>> loader() {
        return service.loader();
    }

    @Override
    public DeletingAspect<Long, Result<List<Street>>> deleter() {
        return service.deleter();
    }

    @Override
    public PredicateAspect<Predicate, Result<List<Street>>> executor() {
        return service.executor();
    }
}
