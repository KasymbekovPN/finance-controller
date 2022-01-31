package kpn.financecontroller.data.propertyExtractors;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class IECollectorImplTest {

    private static final Long ID = 1L;
    private static final Long WRONG_ID = 2L;
    private static final String VALUE = "value";

    @Test
    void shouldCheckSettingGettingOfDeleteBefore() {
        IECollectorImpl<Long, String> collector = new IECollectorImpl<>();
        collector.setDeleteBefore(true);
        assertThat(collector.getDeleteBefore()).isTrue();
    }

    @Test
    void shouldCheckAttemptNonExistEntityGettingWithoutSetting() {
        IECollectorImpl<Long, String> collector = new IECollectorImpl<>();
        assertThat(collector.getEntity(WRONG_ID)).isEmpty();
    }

    @Test
    void shouldCheckAttemptNonExistEntityGetting() {
        IECollectorImpl<Long, String> collector = new IECollectorImpl<>();
        collector.setEntities(Map.of(ID, VALUE));
        assertThat(collector.getEntity(WRONG_ID)).isEmpty();
    }

    @Test
    void shouldCheckEntityGetting() {
        IECollectorImpl<Long, String> collector = new IECollectorImpl<>();
        collector.setEntities(Map.of(ID, VALUE));
        Optional<String> maybeEntity = collector.getEntity(ID);
        assertThat(maybeEntity).isPresent();
        assertThat(maybeEntity).isPresent();
    }
}