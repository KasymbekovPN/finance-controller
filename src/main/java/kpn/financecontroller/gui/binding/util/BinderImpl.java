package kpn.financecontroller.gui.binding.util;

import java.util.HashMap;
import java.util.Map;

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
    public synchronized boolean isBound(K k) {
        return map.containsKey(k);
    }
}
