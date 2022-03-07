package kpn.financecontroller.initialization.factory;

import kpn.financecontroller.data.domains.AbstractDomain;
import kpn.financecontroller.data.entities.AbstractEntity;
import kpn.financecontroller.initialization.collector.LongKeyInitialEntityCollector;
import kpn.financecontroller.initialization.context.ContextImpl;
import kpn.financecontroller.initialization.entities.AbstractInitialEntity;
import kpn.financecontroller.initialization.task.ConversionTask;
import kpn.financecontroller.initialization.task.SavingTask;
import kpn.financecontroller.initialization.task.Task;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class SavingTaskFactoryTest {

    @Test
    void shouldCheckEmptyUsing() {
        SavingTaskFactory factory = new SavingTaskFactory(List.of(), Map.of());
        Optional<Task> maybeTask = factory.getNextIfExist();
        assertThat(maybeTask).isEmpty();
    }

    @Test
    void shouldCheckFactoryUsing() {
        String key0 = "KEY0";

        LongKeyInitialEntityCollector<TestInitialEntity> collector = new LongKeyInitialEntityCollector<>();
        collector.setEntities(
                Map.of(1L, new TestInitialEntity(1L), 2L, new TestInitialEntity(2L))
        );

        ContextImpl context = new ContextImpl();
        context.put(key0, ConversionTask.Properties.RESULT.getValue(), collector);

        List<String> keys = List.of(key0);
        Map<String, Function<Long, SavingTask<?, ?, ?>>> creators = Map.of(
                key0, new TestCreator(key0)
        );
        SavingTaskFactory factory = new SavingTaskFactory(keys, creators);
        factory.setContext(context);

        for (int i = 0; i < 3; i++) {
            Optional<Task> maybeTask = factory.getNextIfExist();
            assertThat(maybeTask.isPresent()).isEqualTo(i < 2);
        }
    }

    @RequiredArgsConstructor
    private static class TestCreator implements Function<Long, SavingTask<?, ?, ?>>{
        private final String key;

        @Override
        public SavingTask<?, ?, ?> apply(Long entityId) {
            return new SavingTask<>(key, entityId, null, null, null, null);
        }
    }

    private static class TestInitialEntity extends AbstractInitialEntity{
        public TestInitialEntity(Long id) {
            this.id = id;
        }
    }
    private static class TestEntity extends AbstractEntity{}
    private static class TestDomain extends AbstractDomain{}
}