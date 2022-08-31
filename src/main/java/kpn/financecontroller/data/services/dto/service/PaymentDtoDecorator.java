package kpn.financecontroller.data.services.dto.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domain.Payment;
import kpn.lib.aspect.deleting.DeletingAspect;
import kpn.lib.aspect.loading.LoadingAspect;
import kpn.lib.aspect.predicate.PredicateAspect;
import kpn.lib.aspect.saving.SavingAspect;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class PaymentDtoDecorator implements Service<Long, Payment, Predicate, Result<List<Payment>>> {
    private final Service<Long, Payment, Predicate, Result<List<Payment>>> service;

    @Override
    public SavingAspect<Payment, Result<List<Payment>>> saver() {
        return service.saver();
    }

    @Override
    public LoadingAspect<Long, Result<List<Payment>>> loader() {
        return service.loader();
    }

    @Override
    public DeletingAspect<Long, Result<List<Payment>>> deleter() {
        return service.deleter();
    }

    @Override
    public PredicateAspect<Predicate, Result<List<Payment>>> executor() {
        return service.executor();
    }
}
