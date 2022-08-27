package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.lib.domain.Domain;
import kpn.lib.result.Result;
import kpn.lib.service.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseServiceWrapper<D extends Domain<Long>> implements ServiceWrapper<D> {
    // TODO: 27.08.2022 !!!
//    private static final ServiceStorage SERVICE_STORAGE = new ServiceStorage();
//
//    public static void registerService(Service<Long, ?, Predicate, Result<List<?>>> service){
//        SERVICE_STORAGE.register(service);
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

    // TODO: 27.08.2022 !!!
//    private static class ServiceStorage {
//        private final Map<Class<?>, Service<Long, ?, Predicate, Result<List<?>>>> services = new HashMap<>();
//
//        public void register(Service<Long, ?, Predicate, Result<List<?>>> service) {
//            services.put(service.getClass(), service);
//        }
//    }
}
