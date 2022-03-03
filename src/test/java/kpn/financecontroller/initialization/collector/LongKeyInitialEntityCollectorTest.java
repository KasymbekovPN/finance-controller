package kpn.financecontroller.initialization.collector;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class LongKeyInitialEntityCollectorTest {


    @Test
    void shouldCheckSettingGetting() {
        HashMap<Long, String> entities = new HashMap<>() {{
            put(1L, "1");
            put(2L, "2");
        }};
        LongKeyInitialEntityCollector<String> collector = new LongKeyInitialEntityCollector<>();
        collector.setEntities(entities);
        assertThat(entities).isEqualTo(collector.getEntities());
    }

    @Test
    void shouldCheckEntityGetting_IfItNotExist() {
        LongKeyInitialEntityCollector<String> collector = new LongKeyInitialEntityCollector<>();
        collector.setEntities(Map.of(1L, "1"));
        Optional<String> maybeEntity = collector.getEntity(123L);
        assertThat(maybeEntity).isEmpty();
    }

    @Test
    void shouldCheckEntityGetting_IfItExist() {
        LongKeyInitialEntityCollector<String> collector = new LongKeyInitialEntityCollector<>();
        long key = 1L;
        String entity = "1";
        collector.setEntities(Map.of(key, entity));
        Optional<String> maybeEntity = collector.getEntity(key);
        assertThat(maybeEntity).isPresent();
        assertThat(entity).isEqualTo(maybeEntity.get());
    }
}