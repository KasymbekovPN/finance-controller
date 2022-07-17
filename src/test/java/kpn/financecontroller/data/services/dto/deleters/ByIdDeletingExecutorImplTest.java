package kpn.financecontroller.data.services.dto.deleters;

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

class ByIdDeletingExecutorImplTest {
    private static final String EXECUTOR_ID = "some.id";
    private static final Long ID = 1L;

    @Test
    void shouldCheckDeleting_ifFail() {
        Throwable throwable = Assertions.catchThrowable(() -> {
            ExecutorResult<Domain<Long>> result = new ByIdDeletingExecutorImpl<>(EXECUTOR_ID, createFailRepository()).delete(ID);
        });

        assertThat(throwable)
                .isInstanceOf(DTOException.class);
        assertThat(new DTOExceptionChecker().check(
                (DTOException) throwable,
                "executor.deleting.byId.fail",
                EXECUTOR_ID, String.valueOf(ID)
        )).isTrue();
    }

    private TestJpaRepository createFailRepository() {
        TestJpaRepository repository = Mockito.mock(TestJpaRepository.class);
        Mockito
                .doThrow(new MockitoException(""))
                .when(repository)
                .deleteById(Mockito.anyObject());
        return repository;
    }

    @Test
    void shouldCheckDeleting() throws DTOException {
        ExecutorResult<TestDomain> result
                = new ByIdDeletingExecutorImpl<TestDomain, TestEntity>(EXECUTOR_ID, createRepository()).delete(ID);

        assertThat(result).isEqualTo(new DefaultExecutorResult<>());
    }

    private TestJpaRepository createRepository() {
        return Mockito.mock(TestJpaRepository.class);
    }
}