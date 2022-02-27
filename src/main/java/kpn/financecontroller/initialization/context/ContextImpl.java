package kpn.financecontroller.initialization.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ContextImpl implements Context{

    private final Map<String, Map<String, Object>> storage = new HashMap<>();

    @Override
    public Optional<Object> get(String key, String property) {
        return storage.containsKey(key) && storage.get(key).containsKey(property)
                ? Optional.of(storage.get(key).get(property))
                : Optional.empty();
    }

    @Override
    public void put(String key, String property, Object value) {
        if (!storage.containsKey(key)){
            storage.put(key, new HashMap<>());
        }
        storage.get(key).put(property, value);
    }
}
