package kpn.financecontroller.data.services.dto.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domain.Seller;
import kpn.lib.aspect.deleting.DeletingAspect;
import kpn.lib.aspect.loading.LoadingAspect;
import kpn.lib.aspect.predicate.PredicateAspect;
import kpn.lib.aspect.saving.SavingAspect;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class SellerDtoDecorator implements Service<Long, Seller, Predicate, Result<List<Seller>>> {
    private final Service<Long, Seller, Predicate, Result<List<Seller>>> service;

    @Override
    public SavingAspect<Seller, Result<List<Seller>>> saver() {
        return service.saver();
    }

    @Override
    public LoadingAspect<Long, Result<List<Seller>>> loader() {
        return service.loader();
    }

    @Override
    public DeletingAspect<Long, Result<List<Seller>>> deleter() {
        return service.deleter();
    }

    @Override
    public PredicateAspect<Predicate, Result<List<Seller>>> executor() {
        return service.executor();
    }
}
