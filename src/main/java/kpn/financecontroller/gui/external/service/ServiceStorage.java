package kpn.financecontroller.gui.external.service;

public interface ServiceStorage {
    Object register(Object service);
    <T> T get(Class<T> type);
}
