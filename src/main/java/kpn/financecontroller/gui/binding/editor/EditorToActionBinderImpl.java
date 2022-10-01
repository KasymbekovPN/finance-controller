package kpn.financecontroller.gui.binding.editor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public final class EditorToActionBinderImpl implements EditorToActionBinder<String, Integer> {
    private final Map<String, Item> binding = new HashMap<>();
    private final Map<Integer, String> byE = new HashMap<>();

    @Override
    public synchronized void registerKey(String key) {
        if (!binding.containsKey(key)){
            binding.put(key, new Item(null));
        }
    }

    @Override
    public synchronized void unregister(String key) {
        if (binding.containsKey(key) && !binding.get(key).isEmpty()){
            byE.remove(binding.get(key).getValue());
        }
        binding.remove(key);
    }

    @Override
    public synchronized boolean checkKey(String key) {
        return binding.containsKey(key);
    }

    @Override
    public synchronized boolean bind(String key, Integer e) {
        if (binding.containsKey(key) && binding.get(key).isEmpty() && !byE.containsKey(e)){
            binding.put(key, new Item(e));
            return true;
        }
        return false;
    }

    @Override
    public synchronized void unbind(Integer e) {
        if (byE.containsKey(e)){
            String key = byE.get(e);
            binding.remove(key);
            byE.remove(e);
        }
    }

    @Override
    public synchronized boolean checkBinding(Integer e) {
        return byE.containsKey(e);
    }

    @RequiredArgsConstructor
    @EqualsAndHashCode
    private static final class Item {
        @Getter
        private final Integer value;

        boolean isEmpty(){
            return value == null;
        }
    }
}
