package kpn.financecontroller.data.services.dto.loaders;

import kpn.lib.exception.DTOException;
import kpn.lib.executor.DefaultExecutorResult;
import kpn.lib.executor.ExecutorResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.data.jpa.repository.JpaRepository;
import support.DTOExceptionChecker;
import support.TestDomain;
import support.TestEntity;
import support.TestJpaRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ByIdLoadingExecutorImplTest {

    private static final String NAME = "name";
    private static final String EXECUTOR_ID = "some.id";
    private static final Long ID = 1L;

    @Test
    void shouldCheckLoading_ifFail() {
        Throwable throwable = Assertions.catchThrowable(() -> {
            ExecutorResult<TestDomain> result = new ByIdLoadingExecutorImpl<TestDomain, TestEntity>(
                    EXECUTOR_ID,
                    createFailRepository(),
                    TestDomain::new
            ).load(1L);
        });
        assertThat(throwable)
                .isInstanceOf(DTOException.class);
        assertThat(new DTOExceptionChecker().check(
                (DTOException) throwable,
                "executor.loading.byId.fail",
                EXECUTOR_ID, String.valueOf(ID)
        )).isTrue();
    }

    private JpaRepository<TestEntity, Long> createFailRepository() {
        TestJpaRepository repository = Mockito.mock(TestJpaRepository.class);
        Mockito
                .when(repository.findById(Mockito.anyObject()))
                .thenThrow(new MockitoException(""));
        return repository;
    }

    @Test
    void shouldCheckLoading() throws DTOException {
        long id = 1L;
        TestDomain domain = new TestDomain();
        domain.setId(id);
        domain.setName(NAME);
        DefaultExecutorResult<TestDomain> expectedResult = new DefaultExecutorResult<>(domain);

        ExecutorResult<TestDomain> result = new ByIdLoadingExecutorImpl<TestDomain, TestEntity>(
                EXECUTOR_ID,
                createRepository(id),
                TestDomain::new
        ).load(id);

        assertThat(result).isEqualTo(expectedResult);
    }

    private JpaRepository<TestEntity, Long> createRepository(long id) {
        TestEntity entity = new TestEntity();
        entity.setId(id);
        entity.setName(NAME);

        TestJpaRepository repository = Mockito.mock(TestJpaRepository.class);
        Mockito
                .when(repository.findById(id))
                .thenReturn(Optional.of(entity));
        return repository;
    }
}