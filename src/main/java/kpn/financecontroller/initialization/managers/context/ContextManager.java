package kpn.financecontroller.initialization.managers.context;

import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.result.Result;

public interface ContextManager {
    void put(Valued<String> key, Valued<String> property, Object value);
    Result<Object> get(Valued<String> key, Valued<String> property);
    <T> Result<T> get(Valued<String> key, Valued<String> property, Class<T> type);
}
