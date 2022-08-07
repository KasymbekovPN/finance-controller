package kpn.financecontroller.data.services.dto.executor;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import kpn.lib.exception.DTOException;
import kpn.lib.executor.DefaultExecutorResult;
import kpn.lib.executor.ExecutorResult;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import support.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class PredicateExecutorImplTest {
    private static final String NAME = "name";
    private static final String EXECUTOR_ID = "some.id";

    private final BooleanExpression predicate = QTestEntity.testEntity.name.eq(NAME);

    @Test
    void shouldCheckExecution_ifFail() {
        Throwable throwable = catchThrowable(() -> {
            ExecutorResult<TestDomain> result = new PredicateExecutorImpl<TestDomain, TestEntity>(
                    EXECUTOR_ID,
                    createFailRepository(predicate),
                    TestDomain::new
            ).execute(predicate);
        });

        assertThat(throwable)
                .isInstanceOf(DTOException.class);
        assertThat(new DTOExceptionChecker().check(
                (DTOException) throwable,
                "executor.predicate.fail",
                EXECUTOR_ID
        )).isTrue();
    }

    private TestQuerydslRepository createFailRepository(Predicate predicate) {
        TestQuerydslRepository repository = Mockito.mock(TestQuerydslRepository.class);
        Mockito
                .when(repository.findAll(predicate))
                .thenThrow(new MockitoException(""));
        return repository;
    }

    @Test
    void shouldCheckExecution() throws DTOException {
        List<Long> ids = List.of(1L, 2L, 3L);
        DefaultExecutorResult<TestDomain> expectedResult = new DefaultExecutorResult<>(
                ids.stream().map(id -> {
                    TestDomain domain = new TestDomain();
                    domain.setId(id);
                    domain.setName(NAME);
                    return domain;
                }).collect(Collectors.toList())
        );

        ExecutorResult<TestDomain> result = new PredicateExecutorImpl<TestDomain, TestEntity>(
                EXECUTOR_ID,
                creteRepository(predicate, ids),
                TestDomain::new
        ).execute(predicate);

        assertThat(result).isEqualTo(expectedResult);
    }

    private TestQuerydslRepository creteRepository(Predicate predicate, List<Long> ids) {
        List<TestEntity> entities = ids.stream().map(id -> {
            TestEntity entity = new TestEntity();
            entity.setId(id);
            entity.setName(NAME);
            return entity;
        }).collect(Collectors.toList());

        TestQuerydslRepository repository = Mockito.mock(TestQuerydslRepository.class);
        Mockito
                .when(repository.findAll(predicate))
                .thenReturn(entities);
        return repository;
    }
}