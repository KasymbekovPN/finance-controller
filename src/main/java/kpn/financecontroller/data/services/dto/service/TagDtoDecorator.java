package kpn.financecontroller.data.services.dto.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domain.Tag;
import kpn.lib.aspect.deleting.DeletingAspect;
import kpn.lib.aspect.loading.LoadingAspect;
import kpn.lib.aspect.predicate.PredicateAspect;
import kpn.lib.aspect.saving.SavingAspect;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class TagDtoDecorator implements Service<Long, Tag, Predicate, Result<List<Tag>>> {
    private final Service<Long, Tag, Predicate, Result<List<Tag>>> service;

    @Override
    public SavingAspect<Tag, Result<List<Tag>>> saver() {
        return service.saver();
    }

    @Override
    public LoadingAspect<Long, Result<List<Tag>>> loader() {
        return service.loader();
    }

    @Override
    public DeletingAspect<Long, Result<List<Tag>>> deleter() {
        return service.deleter();
    }

    @Override
    public PredicateAspect<Predicate, Result<List<Tag>>> executor() {
        return service.executor();
    }
}
