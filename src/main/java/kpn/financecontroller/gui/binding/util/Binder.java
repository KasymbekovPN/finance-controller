package kpn.financecontroller.gui.binding.util;

public interface Binder<K, V> {
    boolean bind(K k, V v);
    boolean unbind(K k);
    boolean isBound(K k);
}
