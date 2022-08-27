package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.lib.domain.Domain;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import kpn.lib.service.Service;

import java.util.List;

public abstract class BaseServiceWrapper<D extends Domain<Long>> implements ServiceWrapper<D> {
    private static final ServiceStorage SERVICE_STORAGE = new ServiceStorageImpl();

    public static void registerService(Object service){
        SERVICE_STORAGE.register(service);
    }

    public static void unregisterService(Class<?> type){
        SERVICE_STORAGE.unregister(type);
    }

    @Override
    public Result<List<D>> find(Predicate predicate) {
        Class<? extends Service<Long, D, Predicate, Result<List<D>>>> key = getKey();
        Service<Long, D, Predicate, Result<List<D>>> service = SERVICE_STORAGE.get(key);
        return service != null
                ? service.executor().execute(predicate)
                : createFailResult();
    }

    protected Result<List<D>> createFailResult(){
        String code = "wrapper." + getClass().getSimpleName() + ".service.null";
        return ImmutableResult.<List<D>>fail(code);
    };

    protected abstract Class<? extends Service<Long, D, Predicate, Result<List<D>>>> getKey();
}
