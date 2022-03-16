// TODO: 16.03.2022 del
//package kpn.financecontroller.initialization._task.task;
//
//import kpn.financecontroller.data.entities.AbstractEntity;
//import kpn.financecontroller.initialization._collector.collector.LongKeyInitialEntityCollector;
//import kpn.financecontroller.initialization._context.context.ContextImpl;
//import kpn.financecontroller.initialization._converter.converter.EntityConverter;
//import kpn.financecontroller.initialization._entities.entities.AbstractInitialEntity;
//import kpn.financecontroller.result.Result;
//import lombok.Getter;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.util.Map;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class EntityConversionTaskTest {
//
//    private static final String KEY = "key";
//
//    private static Result<Map<Long, TestEntity>> expectedResultWhenCollectorAbsent;
//    private static Result<Map<Long, TestEntity>> expectedResult;
//
//    @BeforeAll
//    static void beforeAll() {
//        expectedResultWhenCollectorAbsent = Result.<Map<Long, TestEntity>>builder()
//                .success(false)
//                .code(EntityConversionTask.Codes.NO_COLLECTOR.getValue())
//                .arg(KEY)
//                .build();
//
//        expectedResult = Result.<Map<Long, TestEntity>>builder()
//                .success(true)
//                .arg(KEY)
//                .value(Map.of(1L, new TestEntity(1L)))
//                .build();
//    }
//
//    @Test
//    void shouldCheckExecution_whenCollectorAbsents() {
//        ContextImpl context = new ContextImpl();
//        EntityConversionTask<TestInitialEntity, TestEntity> task
//                = new EntityConversionTask<>(KEY, InitialEntityCollectorCreationTask.Properties.RESULT.getValue(), new TestConverter());
//        task.execute(context);
//
//        Optional<Object> maybeCollector = context.get(KEY, EntityConversionTask.Property.RESULT.getValue());
//        assertThat(maybeCollector).isPresent();
//        Result<Map<Long, TestEntity>> result = (Result<Map<Long, TestEntity>>) maybeCollector.get();
//        assertThat(expectedResultWhenCollectorAbsent).isEqualTo(result);
//        assertThat(task.isContinuationPossible()).isFalse();
//    }
//
//    @Test
//    void shouldCheckExecution() {
//        LongKeyInitialEntityCollector<TestInitialEntity> collector = new LongKeyInitialEntityCollector<>();
//        collector.setEntities(Map.of(1L, new TestInitialEntity(1L)));
//
//        ContextImpl context = new ContextImpl();
//        context.put(KEY, InitialEntityCollectorCreationTask.Properties.RESULT.getValue(), collector);
//
//        EntityConversionTask<TestInitialEntity, TestEntity> task
//                = new EntityConversionTask<>(KEY, InitialEntityCollectorCreationTask.Properties.RESULT.getValue(), new TestConverter());
//        task.execute(context);
//
//        Optional<Object> maybeCollector = context.get(KEY, EntityConversionTask.Property.RESULT.getValue());
//        assertThat(maybeCollector).isPresent();
//        Result<Map<Long, TestEntity>> result = (Result<Map<Long, TestEntity>>) maybeCollector.get();
//        assertThat(expectedResult).isEqualTo(result);
//        assertThat(task.isContinuationPossible()).isTrue();
//    }
//
//    @Getter
//    private static class TestInitialEntity extends AbstractInitialEntity{
//        public TestInitialEntity(Long id) {
//            this.id = id;
//        }
//    }
//    @Getter
//    private static class TestEntity extends AbstractEntity{
//        public TestEntity(Long id) {
//            this.id = id;
//        }
//    }
//
//    private static class TestConverter implements EntityConverter<TestInitialEntity, TestEntity> {
//        @Override
//        public TestEntity convert(TestInitialEntity value) {
//            return new TestEntity(value.getId());
//        }
//    }
//}
