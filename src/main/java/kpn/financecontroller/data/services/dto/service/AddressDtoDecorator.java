package kpn.financecontroller.data.services.dto.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domain.Address;
import kpn.lib.aspect.deleting.DeletingAspect;
import kpn.lib.aspect.loading.LoadingAspect;
import kpn.lib.aspect.predicate.PredicateAspect;
import kpn.lib.aspect.saving.SavingAspect;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class AddressDtoDecorator implements Service<Long, Address, Predicate, Result<List<Address>>> {
    private final Service<Long, Address, Predicate, Result<List<Address>>> service;

    @Override
    public SavingAspect<Address, Result<List<Address>>> saver() {
        return service.saver();
    }

    @Override
    public LoadingAspect<Long, Result<List<Address>>> loader() {
        return service.loader();
    }

    @Override
    public DeletingAspect<Long, Result<List<Address>>> deleter() {
        return service.deleter();
    }

    @Override
    public PredicateAspect<Predicate, Result<List<Address>>> executor() {
        return service.executor();
    }
}
