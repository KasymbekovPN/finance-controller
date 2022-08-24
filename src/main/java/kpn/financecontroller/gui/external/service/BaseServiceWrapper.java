package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.lib.domain.Domain;
import kpn.lib.result.Result;
import kpn.lib.service.Service;

import java.util.List;

public abstract class BaseServiceWrapper<D extends Domain<Long>> implements ServiceWrapper<D> {

    @Override
    public Result<List<D>> find(Predicate predicate) {
        Service<Long, D, Predicate, Result<List<D>>> service = getService();
        return service != null
                ? service.executor().execute(predicate)
                : createFailResult();
    }

    protected abstract Result<List<D>> createFailResult();
    protected abstract Service<Long,D, Predicate, Result<List<D>>> getService();
}
