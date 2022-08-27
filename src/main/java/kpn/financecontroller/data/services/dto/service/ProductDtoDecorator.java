package kpn.financecontroller.data.services.dto.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domain.Product;
import kpn.lib.aspect.deleting.DeletingAspect;
import kpn.lib.aspect.loading.LoadingAspect;
import kpn.lib.aspect.predicate.PredicateAspect;
import kpn.lib.aspect.saving.SavingAspect;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class ProductDtoDecorator implements Service<Long, Product, Predicate, Result<List<Product>>> {
    private final Service<Long, Product, Predicate, Result<List<Product>>> service;

    @Override
    public SavingAspect<Product, Result<List<Product>>> saver() {
        return service.saver();
    }

    @Override
    public LoadingAspect<Long, Result<List<Product>>> loader() {
        return service.loader();
    }

    @Override
    public DeletingAspect<Long, Result<List<Product>>> deleter() {
        return service.deleter();
    }

    @Override
    public PredicateAspect<Predicate, Result<List<Product>>> executor() {
        return service.executor();
    }
}
