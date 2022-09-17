package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.lib.domain.Domain;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import kpn.lib.service.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Service<Long, D, Predicate, Result<List<D>>>> maybeService = getService();
        return maybeService.isPresent()
                ? maybeService.get().executor().execute(predicate)
                : createFailResult();
    }

    @Override
    public Result<List<D>> findAll() {
        Optional<Service<Long, D, Predicate, Result<List<D>>>> maybeService = getService();
        return maybeService.isPresent()
                ? maybeService.get().loader().all()
                : createFailResult();
    }

    private Optional<Service<Long, D, Predicate, Result<List<D>>>> getService(){
        return Optional.ofNullable(
                SERVICE_STORAGE.get(getKey())
        );
    }

    protected Result<List<D>> createFailResult(){
        String code = "wrapper." + getClass().getSimpleName() + ".service.null";
        return ImmutableResult.<List<D>>fail(code);
    };

    protected abstract Class<? extends Service<Long, D, Predicate, Result<List<D>>>> getKey();
}
