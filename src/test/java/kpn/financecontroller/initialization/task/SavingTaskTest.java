package kpn.financecontroller.initialization.task;

import kpn.financecontroller.converters.Converter;
import kpn.financecontroller.data.domains.AbstractDomain;
import kpn.financecontroller.data.entities.AbstractEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.data.services.savers.Saver;
import kpn.financecontroller.initialization.collector.LongKeyInitialEntityCollector;
import kpn.financecontroller.initialization.context.Context;
import kpn.financecontroller.initialization.context.ContextImpl;
import kpn.financecontroller.initialization.entities.AbstractInitialEntity;
import kpn.financecontroller.initialization.entityUpdater.EntityUpdater;
import kpn.financecontroller.result.Result;
import lombok.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class SavingTaskTest {

    private static final String KEY = "some.key";
    private static final Long ENTITY_ID = 1L;
    private static final Long DOMAIN_ID = 123L;

    private static TestDomain domain;

    private static Result<Void> expectedResultWhenNoCollector;
    private static Result<Void> expectedResultWhenNoOneEntity;
    private static Result<Void> expectedResultWhenSavingFail;
    private static Result<Void> expectedResult;

    @BeforeAll
    static void beforeAll() {
        domain = new TestDomain(DOMAIN_ID);

        expectedResultWhenNoCollector = Result.<Void>builder()
                .success(false)
                .code(SavingTask.Codes.NO_COLLECTOR.getValue())
                .arg(KEY)
                .build();
        expectedResultWhenNoOneEntity = Result.<Void>builder()
                .success(false)
                .code(SavingTask.Codes.NO_ONE_ENTITY.getValue())
                .arg(KEY)
                .build();
        expectedResultWhenSavingFail = Result.<Void>builder()
                .success(false)
                .code(SavingTask.Codes.SAVING_FAIL.getValue())
                .arg(KEY)
                .arg(ENTITY_ID)
                .build();
        expectedResult = Result.<Void>builder()
                .success(true)
                .code(SavingTask.Codes.SUCCESS.getValue())
                .arg(KEY)
                .build();
    }

    @Test
    void shouldCheckExecution_withoutCollector() {
        ContextImpl context = new ContextImpl();

        SavingTask<TestInitialEntity, TestEntity, TestDomain> task
                = new SavingTask<>(KEY, ENTITY_ID, new TestConverter(), createDTOService(createSaver()), new TestEntityUpdater(), ConversionTask.Properties.RESULT.getValue());
        task.execute(context);
        Optional<Object> maybeResult = context.get(KEY, SavingTask.Properties.RESULT.getValue());

        assertThat(maybeResult).isPresent();
        Result<Void> result = (Result<Void>) maybeResult.get();
        assertThat(expectedResultWhenNoCollector).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isFalse();
    }

    @Test
    void shouldCheckExecution_noOneEntityInCollector() {
        ContextImpl context = new ContextImpl();
        TestCollector collector = new TestCollector();
        collector.setEntities(Map.of());
        context.put(KEY, ConversionTask.Properties.RESULT.getValue(), collector);

        SavingTask<TestInitialEntity, TestEntity, TestDomain> task
                = new SavingTask<>(KEY, ENTITY_ID, new TestConverter(), createDTOService(createSaver()), new TestEntityUpdater(), ConversionTask.Properties.RESULT.getValue());
        task.execute(context);
        Optional<Object> maybeResult = context.get(KEY, SavingTask.Properties.RESULT.getValue());

        assertThat(maybeResult).isPresent();
        Result<Void> result = (Result<Void>) maybeResult.get();
        assertThat(expectedResultWhenNoOneEntity).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isFalse();
    }

    @Test
    void shouldCheckExecution_whenSavingIsFail() {
        TestCollector collector = new TestCollector();
        collector.setEntities(Map.of(ENTITY_ID, new TestInitialEntity(ENTITY_ID)));

        ContextImpl context = new ContextImpl();
        context.put(KEY, ConversionTask.Properties.RESULT.getValue(), collector);

        SavingTask<TestInitialEntity, TestEntity, TestDomain> task
                = new SavingTask<>(KEY, ENTITY_ID, new TestConverter(), createDTOService(createFailSaver()), new TestEntityUpdater(), ConversionTask.Properties.RESULT.getValue());
        task.execute(context);
        Optional<Object> maybeResult = context.get(KEY, SavingTask.Properties.RESULT.getValue());
        assertThat(maybeResult).isPresent();
        Result<Void> result = (Result<Void>) maybeResult.get();
        assertThat(expectedResultWhenSavingFail).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isFalse();
    }

    @Test
    void shouldCheckExecution() {
        TestCollector collector = new TestCollector();
        collector.setEntities(Map.of(ENTITY_ID, new TestInitialEntity(ENTITY_ID)));

        ContextImpl context = new ContextImpl();
        context.put(KEY, ConversionTask.Properties.RESULT.getValue(), collector);

        SavingTask<TestInitialEntity, TestEntity, TestDomain> task
                = new SavingTask<>(KEY, ENTITY_ID, new TestConverter(), createDTOService(createSaver()), new TestEntityUpdater(), ConversionTask.Properties.RESULT.getValue());
        task.execute(context);
        Optional<Object> maybeResult = context.get(KEY, SavingTask.Properties.RESULT.getValue());
        assertThat(maybeResult).isPresent();
        Result<Void> result = (Result<Void>) maybeResult.get();
        assertThat(expectedResult).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isTrue();

        Optional<Object> maybeMatching = context.get(KEY, SavingTask.Properties.MATCHING.getValue());
        assertThat(maybeMatching).isPresent();
        Map<Long, Long> matching = (Map<Long, Long>) maybeMatching.get();
        assertThat(Map.of(ENTITY_ID, DOMAIN_ID)).isEqualTo(matching);
    }

    private static TestSaver createSaver(){
        TestSaver saver = Mockito.mock(TestSaver.class);
        Mockito
                .when(saver.save(Mockito.any(TestEntity.class)))
                .thenReturn(Result.<TestDomain>builder().success(true).value(domain).build());
        return saver;
    }

    private static TestSaver createFailSaver(){
        TestSaver saver = Mockito.mock(TestSaver.class);
        Mockito
                .when(saver.save(Mockito.any(TestEntity.class)))
                .thenReturn(Result.<TestDomain>builder().success(false).build());
        return saver;
    }

    private static DTOService<TestDomain, TestEntity, Long> createDTOService(TestSaver testSaver ){
        TestDTOService dtoService = Mockito.mock(TestDTOService.class);
        Mockito
                .when(dtoService.saver())
                .thenReturn(testSaver);
        return dtoService;
    }

    private abstract static class TestSaver implements Saver<TestDomain, TestEntity, Long>{}
    private abstract static class TestDTOService implements DTOService<TestDomain, TestEntity, Long>{}

    @EqualsAndHashCode(callSuper = false)
    private static class TestCollector extends LongKeyInitialEntityCollector<TestInitialEntity> {}

    @Getter
    @Setter
    @NoArgsConstructor
    private static class TestInitialEntity extends AbstractInitialEntity {
        public TestInitialEntity(Long id) {
            this.id = id;
        }
    }

    @Getter
    @Setter
    private static class TestEntity extends AbstractEntity {
        public TestEntity(Long id) {
            this.id = id;
        }
    }

    @Getter
    @Setter
    private static class TestDomain extends AbstractDomain {
        public TestDomain(Long id) {
            this.id = id;
        }
    }

    private static class TestConverter implements Converter<TestInitialEntity, TestEntity>{
        @Override
        public TestEntity convert(TestInitialEntity value) {
            return new TestEntity(value.getId());
        }
    }

    private static class TestEntityUpdater implements EntityUpdater<TestInitialEntity>{

        @Override
        public TestInitialEntity update(Context context, TestInitialEntity entity) {
            return entity;
        }
    }
}
