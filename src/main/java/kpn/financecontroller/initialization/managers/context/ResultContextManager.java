package kpn.financecontroller.initialization.managers.context;

import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.result.Result;

public interface ResultContextManager {
    <T> void put(Valued<String> k, Valued<String> p, Result<T> result);
    Result<Object> get(Valued<String> k, Valued<String> p);
    <T> Result<T> get(Valued<String> k, Valued<String> p, Class<T> type);
}
