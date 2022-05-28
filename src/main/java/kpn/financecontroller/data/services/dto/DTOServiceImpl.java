package kpn.financecontroller.data.services.dto;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.services.dto.deleters.Deleter;
import kpn.financecontroller.data.services.dto.executors.PredicateExecutor;
import kpn.financecontroller.data.services.dto.loaders.Loader;
import kpn.financecontroller.data.services.dto.savers.Saver;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DTOServiceImpl<DOMAIN, ENTITY> implements DTOService<DOMAIN, ENTITY> {
    private final Saver<DOMAIN, ENTITY, Long> saver;
    private final Loader<DOMAIN, ENTITY, Long> loader;
    private final Deleter<DOMAIN, ENTITY, Long> deleter;
    private final PredicateExecutor<DOMAIN, Predicate> executor;

    @Override
    public Saver<DOMAIN, ENTITY, Long> saver() {
        return saver;
    }

    @Override
    public Loader<DOMAIN, ENTITY, Long> loader() {
        return loader;
    }

    @Override
    public Deleter<DOMAIN, ENTITY, Long> deleter() {
        return deleter;
    }

    @Override
    public PredicateExecutor<DOMAIN, Predicate> executor() {
        return executor;
    }
}
