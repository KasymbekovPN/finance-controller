package kpn.financecontroller.initialization.load.tasks;

import kpn.financecontroller.initialization.load.collectors.LoadDataCollector;
import kpn.financecontroller.initialization.load.calculators.PathCalculator;
import kpn.financecontroller.initialization.load.creators.CollectorCreator;
import kpn.financecontroller.result.Result;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class LoadingTaskImplTest {

    private static final String PATH = "path";
    private static final String ID = "id";
    private static final String KEY = "key";
    private static final String COLLECTOR_FILLING_CODE = "collector.filling.code";

    private static LoadingTaskImpl<Long, String> task;
    private static Result<String> pathCalculatorResult;
    private static Result<String> expectedResultOfPathCalculationMethod;
    private static Result<Void> expectedResultAfterCollectorFilling;
    private static TestCollector expectedCollector;

    @BeforeAll
    static void beforeAll() {
        pathCalculatorResult = Result.<String>builder()
                .success(true)
                .value(PATH)
                .build();
        expectedResultOfPathCalculationMethod = Result.<String>builder()
                .success(true)
                .value(PATH)
                .arg(ID)
                .build();
        expectedResultAfterCollectorFilling = Result.<Void>builder()
                .success(true)
                .code(COLLECTOR_FILLING_CODE)
                .build();

        expectedCollector = new TestCollector(KEY);

        PathCalculator<String, String> pathCalculator = createPathCalculator();
        CollectorCreator<Long, String> collectorCreator = createCollectorCreator();
        task = new LoadingTaskImpl<>(pathCalculator, collectorCreator, PATH, ID);
    }

    @Test
    void shouldCheckPathCalculation() {
        Result<String> result = task.calculatePath();
        assertThat(expectedResultOfPathCalculationMethod).isEqualTo(result);
    }

    @Test
    void shouldCheckCollectorFilling() {
        Result<Void> result = task.fillCollector("");
        assertThat(expectedResultAfterCollectorFilling).isEqualTo(result);
    }

    @Test
    void shouldCheckCollectorGetting() {
        task.fillCollector("");
        LoadDataCollector<Long, String> collector = task.getCollector();
        assertThat(expectedCollector).isEqualTo(collector);
    }

    private static CollectorCreator<Long, String> createCollectorCreator() {
        TestCollectorCreator collectorCreator = Mockito.mock(TestCollectorCreator.class);
        Mockito
                .when(collectorCreator.create(Mockito.anyString()))
                .thenReturn(
                        Result.<LoadDataCollector<Long, String>>builder().success(true).code(COLLECTOR_FILLING_CODE).value(new TestCollector(KEY)).build()
                );
        return collectorCreator;
    }

    private static PathCalculator<String, String> createPathCalculator() {
        TestPathCalculator pathCalculator = Mockito.mock(TestPathCalculator.class);
        Mockito
                .when(pathCalculator.calculate(Mockito.anyString()))
                .thenReturn(pathCalculatorResult);
        return pathCalculator;
    }

    abstract private static class TestPathCalculator implements PathCalculator<String, String>{}
    abstract private static class TestCollectorCreator implements CollectorCreator<Long, String>{}

    @AllArgsConstructor
    @EqualsAndHashCode
    private static class TestCollector implements LoadDataCollector<Long, String> {
        private final String key;

        @Override
        public void setDeleteBefore(Boolean deleteBefore) {}
        @Override
        public Boolean getDeleteBefore() {return null;}
        @Override
        public void setEntities(Map<Long, String> entities) {}
        @Override
        public Optional<String> getEntity(Long key) {return Optional.empty();}
    }
}