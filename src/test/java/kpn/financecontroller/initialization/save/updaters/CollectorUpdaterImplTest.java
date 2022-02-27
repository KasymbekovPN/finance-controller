package kpn.financecontroller.initialization.save.updaters;

import kpn.financecontroller.initialization.old.collectors.LoadDataCollectorImpl;
import kpn.financecontroller.initialization.old.entities.AbstractInitialEntity;
import kpn.financecontroller.initialization.old.save.updaters.CollectorUpdaterImpl;
import lombok.EqualsAndHashCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class CollectorUpdaterImplTest {

    private static HashMap<Long, Entity> expectedAfterEmptyUpdatingCollectorData;
    private static HashMap<Long, Entity> expectedAfterAddUpdatingCollectorData;
    private static HashMap<Object, Object> expectedAfterResetUpdatingCollectorData;

    @BeforeAll
    static void beforeAll() {
        expectedAfterEmptyUpdatingCollectorData = new HashMap<>() {{
            put(1L, new Entity(1L));
            put(2L, new Entity(2L));
        }};
        expectedAfterAddUpdatingCollectorData = new HashMap<>() {{
            put(11L, new Entity(11L));
            put(21L, new Entity(21L));
        }};
        expectedAfterResetUpdatingCollectorData = new HashMap<>() {{
            put(1L, new Entity(1L));
            put(2L, new Entity(2L));
        }};
    }

    @Test
    void shouldCheckUpdating_whenChangingQueueEmpty() {
        TestUpdaterImpl updater = new TestUpdaterImpl();
        TestCollector collector = new TestCollector();
        collector.setEntities(new HashMap<>(createOriginalData()));
        updater.update(collector);
        Assertions.assertThat(expectedAfterEmptyUpdatingCollectorData).isEqualTo(collector.getEntities());
    }

    @Test
    void shouldCheckUpdating_whenChangingQueueNotEmpty() {
        TestUpdaterImpl updater = new TestUpdaterImpl();
        TestCollector collector = new TestCollector();
        collector.setEntities(new HashMap<>(createOriginalData()));
        updater
                .add(1L, 11L)
                .add(2L, 21L)
                .update(collector);
        Assertions.assertThat(expectedAfterAddUpdatingCollectorData).isEqualTo(collector.getEntities());
    }

    @Test
    void shouldCheckUpdating_afterReset() {
        TestUpdaterImpl updater = new TestUpdaterImpl();
        TestCollector collector = new TestCollector();
        collector.setEntities(new HashMap<>(createOriginalData()));
        updater
                .add(1L, 11L)
                .add(2L, 21L)
                .reset()
                .update(collector);
        Assertions.assertThat(expectedAfterResetUpdatingCollectorData).isEqualTo(collector.getEntities());
    }

    private static Map<Long, Entity> createOriginalData(){
        return new HashMap<>() {{
            put(1L, new Entity(1L));
            put(2L, new Entity(2L));
        }};
    }

    @EqualsAndHashCode(callSuper = true)
    private static class Entity extends AbstractInitialEntity<Long>{
        public Entity(Long id) {
            setId(id);
        }
    }

    private static class TestUpdaterImpl extends CollectorUpdaterImpl<Long, Entity> {}

    private static class TestCollector extends LoadDataCollectorImpl<Long, Entity>{}
}