package kpn.financecontroller.data.services.dto.deleter;

import kpn.lib.domain.Domain;
import kpn.lib.exception.DTOException;
import kpn.lib.executor.DefaultExecutorResult;
import kpn.lib.executor.ExecutorResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import support.DTOExceptionChecker;
import support.TestDomain;
import support.TestEntity;
import support.TestJpaRepository;

import static org.assertj.core.api.Assertions.assertThat;

class CompletelyDeletingExecutorImplTest {
    private static final String EXECUTOR_ID = "some.id";

    @Test
    void shouldCheckDeleting_ifFail() {
        Throwable throwable = Assertions.catchThrowable(() -> {
            ExecutorResult<Domain<Long>> result
                    = new CompletelyDeletingExecutorImpl<>(EXECUTOR_ID, createFailRepository()).delete();
        });

        assertThat(throwable)
                .isInstanceOf(DTOException.class);
        assertThat(new DTOExceptionChecker().check(
                (DTOException) throwable,
                "executor.deleting.completely.fail",
                EXECUTOR_ID
        )).isTrue();
    }

    private TestJpaRepository createFailRepository() {
        TestJpaRepository repository = Mockito.mock(TestJpaRepository.class);
        Mockito
                .doThrow(new MockitoException(""))
                .when(repository)
                .deleteAll();
        return repository;
    }

    @Test
    void shouldCheckDeleting() throws DTOException {
        ExecutorResult<TestDomain> result
                = new CompletelyDeletingExecutorImpl<TestDomain, TestEntity>(EXECUTOR_ID, createRepository()).delete();

        assertThat(result).isEqualTo(new DefaultExecutorResult<>());
    }

    private TestJpaRepository createRepository() {
        return Mockito.mock(TestJpaRepository.class);
    }
}