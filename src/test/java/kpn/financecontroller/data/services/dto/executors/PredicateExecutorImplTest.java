package kpn.financecontroller.data.services.dto.executors;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import kpn.lib.domain.AbstractDomain;
import kpn.lib.entity.AbstractEntity;
import kpn.lib.exception.DTOException;
import kpn.lib.executor.DefaultExecutorResult;
import kpn.lib.executor.ExecutorResult;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.stream.Collectors;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class PredicateExecutorImplTest {
    private static final String NAME = "name";

    private final BooleanExpression predicate = QTestEntity.testEntity.name.eq(NAME);

    @Test
    void shouldCheckExecution_ifFail() {
        Throwable throwable = catchThrowable(() -> {
            ExecutorResult<TestDomain> result
                    = new PredicateExecutorImpl<TestDomain, TestEntity>(createFailRepository(predicate), TestDomain::new).execute(predicate);
        });
        assertThat(throwable)
                .isInstanceOf(DTOException.class)
                .hasMessage("executor.predicate.fail");
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

        ExecutorResult<TestDomain> result
                = new PredicateExecutorImpl<TestDomain, TestEntity>(creteRepository(predicate, ids), TestDomain::new).execute(predicate);
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

    // TODO: 14.07.2022 move to file
    private abstract static class TestQuerydslRepository implements QuerydslPredicateExecutor<TestEntity>{}

    // TODO: 14.07.2022 move to file
    @Getter
    @Setter
    @NoArgsConstructor
    private static class TestEntity extends AbstractEntity<Long> {
        private Long id;
        private String name;

        public TestEntity(TestDomain domain) {
            setId(domain.getId());
            setName(domain.getName());
        }
    }

    // TODO: 14.07.2022 move to file
    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = true)
    @NoArgsConstructor
    private static class TestDomain extends AbstractDomain<Long> {
        private String name;
        public TestDomain(TestEntity entity) {
            setId(entity.getId());
            setName(entity.getName());
        }
    }

    // TODO: 16.07.2022 move to file
    private static class QTestEntity extends EntityPathBase<TestEntity>{
        public static final QTestEntity testEntity = new QTestEntity("");

        public final NumberPath<Long> id = createNumber("id", Long.class);
        public final StringPath name = createString("name");

        public QTestEntity(String variable) {
            super(TestEntity.class, forVariable(variable));
        }

        public QTestEntity(Path<? extends TestEntity> path) {
            super(path.getType(), path.getMetadata());
        }

        public QTestEntity(PathMetadata metadata) {
            super(TestEntity.class, metadata);
        }
    }
}