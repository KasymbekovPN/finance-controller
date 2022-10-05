package kpn.financecontroller.gui.binding.editor;

// TODO: 05.10.2022 del
// TODO: 03.10.2022 if refactoring - method must be atomic
public interface EditorToActionBinder<K, E> {
    void registerKey(K key);
    void unregister(K key);
    boolean checkKey(K key);
    boolean bind(K key, E e);
    void unbind(E e);
    boolean checkBinding(E e);
}
