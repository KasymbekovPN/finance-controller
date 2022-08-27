package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.lib.domain.Domain;
import kpn.lib.result.Result;
import kpn.lib.service.Service;

import java.util.List;

public abstract class BaseServiceWrapper<D extends Domain<Long>> implements ServiceWrapper<D> {
    private static final ServiceStorage SERVICE_STORAGE = new ServiceStorageImpl();

//    public static void registerService(Object service){
//        SERVICE_STORAGE.register(service);
//    }
//
//    protected static <T> T get(Class<T> type){
//        return SERVICE_STORAGE.get(type);
//    }

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
