package kpn.financecontroller.gui.binding.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BinderImpl<K, V> implements Binder<K, V> {
    private final Map<K, V> map = new HashMap<>();

    private final Object lock = new Object();

    @Override
    public boolean bind(K k, V v) {
        synchronized (lock){
            if (map.containsKey(k)){
                return false;
            }
            map.put(k, v);
            return true;
        }
    }

    @Override
    public boolean unbind(K k) {
        synchronized (lock){
            if (map.containsKey(k)){
                map.remove(k);
                return true;
            }
            return false;
        }
    }

    @Override
    public void changeBinding(K k, V v) {
        synchronized (lock){
            if (map.containsKey(k)){
                unbind(k);
            }
            bind(k, v);
        }
    }

    @Override
    public boolean isBound(K k) {
        synchronized (lock){
            return map.containsKey(k);
        }
    }

    @Override
    public boolean isBound(K k, V v) {
        synchronized (lock){
            return isBound(k) && map.get(k).equals(v);
        }
    }

    @Override
    public Optional<V> get(K k) {
        synchronized (lock){
            return map.containsKey(k)
                    ? Optional.of(map.get(k))
                    : Optional.empty();
        }
    }
}
