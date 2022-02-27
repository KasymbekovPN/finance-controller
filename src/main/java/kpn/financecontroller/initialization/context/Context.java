package kpn.financecontroller.initialization.context;

import java.util.Optional;

public interface Context {
    Optional<Object> get(String key, String property);
    void put(String key, String property, Object value);
}
