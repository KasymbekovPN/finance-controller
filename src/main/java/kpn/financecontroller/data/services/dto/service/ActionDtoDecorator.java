package kpn.financecontroller.data.services.dto.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domain.Action;
import kpn.financecontroller.data.entity.QActionEntity;
import kpn.lib.aspect.deleting.DeletingAspect;
import kpn.lib.aspect.loading.LoadingAspect;
import kpn.lib.aspect.predicate.PredicateAspect;
import kpn.lib.aspect.saving.SavingAspect;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class ActionDtoDecorator implements Service<Long, Action, Predicate, Result<List<Action>>> {
    private final Service<Long, Action, Predicate, Result<List<Action>>> service;

    @Override
    public SavingAspect<Action, Result<List<Action>>> saver() {
        return service.saver();
    }

    @Override
    public LoadingAspect<Long, Result<List<Action>>> loader() {
        return service.loader();
    }

    @Override
    public DeletingAspect<Long, Result<List<Action>>> deleter() {
        return service.deleter();
    }

    @Override
    public PredicateAspect<Predicate, Result<List<Action>>> executor() {
        return service.executor();
    }

    public Result<List<Action>> findByDescriptionSubstring(String substring){
        return substring != null && !substring.isEmpty()
                ? executor().execute(QActionEntity.actionEntity.description.likeIgnoreCase(substring + "%"))
                : loader().all();
    }
}
