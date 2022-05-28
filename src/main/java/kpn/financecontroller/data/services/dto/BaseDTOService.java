package kpn.financecontroller.data.services.dto;

import kpn.financecontroller.data.services.dto.deleters.Deleter;
import kpn.financecontroller.data.services.dto.executors.PredicateExecutor;
import kpn.financecontroller.data.services.dto.loaders.Loader;
import kpn.financecontroller.data.services.dto.savers.Saver;

public interface BaseDTOService<DOMAIN, ENTITY, ID, PREDICATE> {
    Saver<DOMAIN, ENTITY, ID> saver();
    Loader<DOMAIN, ENTITY, ID> loader();
    Deleter<DOMAIN, ENTITY, ID> deleter();
    PredicateExecutor<DOMAIN, PREDICATE> executor();
}
