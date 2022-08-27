package kpn.financecontroller.gui.external.service;

public interface ServiceStorage {
    Object register( Object service);
    void unregister(Class<?> type);
    <T> T get(Class<T> type);
}
