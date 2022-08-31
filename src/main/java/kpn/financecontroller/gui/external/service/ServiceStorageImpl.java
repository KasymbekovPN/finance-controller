package kpn.financecontroller.gui.external.service;

import java.util.HashMap;
import java.util.Map;

final class ServiceStorageImpl implements ServiceStorage {
    private final Map<Class<?>, Object> services = new HashMap<>();

    @Override
    public Object register(Object service) {
        return services.put(service.getClass(), service);
    }

    @Override
    public void unregister(Class<?> type) {
        services.remove(type);
    }

    @Override
    public <T> T get(Class<T> type) {
        return type != null && services.containsKey(type)
                ? type.cast(services.get(type))
                : null;
    }
}
