package kpn.financecontroller.gui.binding.editor;

public interface EditorToActionBinder<K, E> {
    void registerKey(K key);
    void unregister(K key);
    boolean checkKey(K key);
    boolean bind(K key, E e);
    void unbind(E e);
    boolean checkBinding(E e);
}
