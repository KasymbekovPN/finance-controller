package kpn.financecontroller.data.services.dto.executors;

import com.querydsl.core.types.dsl.BooleanExpression;
import kpn.financecontroller.data.services.dto.utils.QTestEntity;
import kpn.financecontroller.data.services.dto.utils.TestEntity;
import kpn.financecontroller.data.services.dto.utils.TestModel;
import kpn.financecontroller.data.services.dto.utils.TestQuerydslPredicateExecutor;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class PredicateExecutorOldImplTest {

    private static final String EXECUTOR_NAME = "some.executor";

    private static BooleanExpression predicate;
    private static List<TestEntity> returnedEntities;
    private static ImmutableResult<List<TestModel>> expectedResultWhenExecutionFail;
    private static ImmutableResult<List<TestModel>> expectedResult;

    @BeforeAll
    static void beforeAll() {
        predicate = QTestEntity.testEntity.value.ne("");

        List<TestModel> expectedDomains = List.of(new TestModel(1L, "name1"), new TestModel(2L, "name2"));
        returnedEntities = expectedDomains.stream().map(TestEntity::new).collect(Collectors.toList());

        expectedResultWhenExecutionFail = ImmutableResult.<List<TestModel>>bFail("executor.execution.fail")
                .arg(EXECUTOR_NAME)
                .build();
        expectedResult = ImmutableResult.<List<TestModel>>bOk(expectedDomains).arg(EXECUTOR_NAME).build();
    }

    @Test
    void shouldCheckExecution_whenExecutionFail() {
        PredicateExecutorOldImpl<TestModel, TestEntity> executor
                = new PredicateExecutorOldImpl<TestModel, TestEntity>(EXECUTOR_NAME, createFailExecutor(), null);
        Result<List<TestModel>> result = executor.execute(predicate);
        assertThat(expectedResultWhenExecutionFail).isEqualTo(result);
    }

    private QuerydslPredicateExecutor<TestEntity> createFailExecutor() {
        TestQuerydslPredicateExecutor executor = Mockito.mock(TestQuerydslPredicateExecutor.class);
        Mockito
                .when(executor.findAll(predicate))
                .thenThrow(new MockitoException(""));
        return executor;
    }

    @Test
    void shouldCheckExecution() {
        PredicateExecutorOldImpl<TestModel, TestEntity> executor
                = new PredicateExecutorOldImpl<TestModel, TestEntity>(EXECUTOR_NAME, createExecutor(), new TestConverter());
        Result<List<TestModel>> result = executor.execute(predicate);
        assertThat(expectedResult).isEqualTo(result);
    }

    private QuerydslPredicateExecutor<TestEntity> createExecutor() {
        TestQuerydslPredicateExecutor executor = Mockito.mock(TestQuerydslPredicateExecutor.class);
        Mockito
                .when(executor.findAll(predicate))
                .thenReturn(returnedEntities);
        return executor;
    }

    private static class TestConverter implements Function<List<TestEntity>, List<TestModel>> {
        @Override
        public List<TestModel> apply(List<TestEntity> testEntities) {
            return testEntities.stream().map(entity -> {
                return new TestModel(entity.getId(), entity.getValue());
            }).collect(Collectors.toList());
        }
    }
}