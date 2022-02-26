package kpn.financecontroller.initialization.save.updaters;

import kpn.financecontroller.initialization.collectors.LoadDataCollector;
import kpn.financecontroller.initialization.entities.AbstractInitialEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class CollectorUpdaterImpl<K, E extends AbstractInitialEntity<K>> implements CollectorUpdater<K, E> {

    private final Deque<Item<K>> deque = new ArrayDeque<>();

    @Override
    public CollectorUpdater<K, E> reset() {
        deque.clear();
        return this;
    }

    @Override
    public CollectorUpdater<K, E> add(K oldKey, K newKey) {
        deque.addLast(new Item<>(oldKey, newKey));
        return this;
    }

    @Override
    public void update(LoadDataCollector<K, E> collector) {
        while (!deque.isEmpty()){
            Item<K> item = deque.pollLast();
            Map<K, E> entities = collector.getEntities();
            K oldKey = item.getOldKey();
            if (entities.containsKey(oldKey)){
                entities.get(oldKey).setId(item.getNewKey());
            }
        }
    }

    @AllArgsConstructor
    @Getter
    private static class Item<K>{
        private K oldKey;
        private K newKey;
    }
}