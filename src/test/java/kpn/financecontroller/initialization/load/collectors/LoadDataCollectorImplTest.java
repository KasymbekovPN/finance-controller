package kpn.financecontroller.initialization.load.collectors;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class LoadDataCollectorImplTest {

    private static final Long ID = 1L;
    private static final Long WRONG_ID = 2L;
    private static final String VALUE = "value";

    @Test
    void shouldCheckSettingGettingOfDeleteBefore() {
        LoadDataCollectorImpl<Long, String> collector = new LoadDataCollectorImpl<>();
        collector.setDeleteBefore(true);
        assertThat(collector.getDeleteBefore()).isTrue();
    }

    @Test
    void shouldCheckAttemptNonExistEntityGettingWithoutSetting() {
        LoadDataCollectorImpl<Long, String> collector = new LoadDataCollectorImpl<>();
        assertThat(collector.getEntity(WRONG_ID)).isEmpty();
    }

    @Test
    void shouldCheckAttemptNonExistEntityGetting() {
        LoadDataCollectorImpl<Long, String> collector = new LoadDataCollectorImpl<>();
        collector.setEntities(Map.of(ID, VALUE));
        assertThat(collector.getEntity(WRONG_ID)).isEmpty();
    }

    @Test
    void shouldCheckEntityGetting() {
        LoadDataCollectorImpl<Long, String> collector = new LoadDataCollectorImpl<>();
        collector.setEntities(Map.of(ID, VALUE));
        Optional<String> maybeEntity = collector.getEntity(ID);
        assertThat(maybeEntity).isPresent();
        assertThat(maybeEntity).isPresent();
    }
}