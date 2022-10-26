package kpn.financecontroller.gui.binding.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// TODO: 22.10.2022 change monitor - not this but Object with name lock
public class BinderImpl<K, V> implements Binder<K, V> {
    private final Map<K, V> map = new HashMap<>();

    @Override
    public synchronized boolean bind(K k, V v) {
        if (map.containsKey(k)){
            return false;
        }
        map.put(k, v);
        return true;
    }

    @Override
    public synchronized boolean unbind(K k) {
        if (map.containsKey(k)){
            map.remove(k);
            return true;
        }
        return false;
    }

    @Override
    public synchronized void changeBinding(K k, V v) {
        if (map.containsKey(k)){
            unbind(k);
        }
        bind(k, v);
    }

    @Override
    public synchronized boolean isBound(K k) {
        return map.containsKey(k);
    }

    @Override
    public synchronized boolean isBound(K k, V v) {
        return isBound(k) && map.get(k).equals(v);
    }

    @Override
    public synchronized Optional<V> get(K k) {
        return map.containsKey(k)
                ? Optional.of(map.get(k))
                : Optional.empty();
    }
}
