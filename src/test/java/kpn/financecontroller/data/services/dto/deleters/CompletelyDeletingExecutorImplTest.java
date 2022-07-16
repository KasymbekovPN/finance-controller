package kpn.financecontroller.data.services.dto.deleters;

import kpn.lib.domain.Domain;
import kpn.lib.exception.DTOException;
import kpn.lib.executor.DefaultExecutorResult;
import kpn.lib.executor.ExecutorResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import support.TestDomain;
import support.TestEntity;
import support.TestJpaRepository;

import static org.assertj.core.api.Assertions.assertThat;

class CompletelyDeletingExecutorImplTest {

    @Test
    void shouldCheckDeleting_ifFail() {
        Throwable throwable = Assertions.catchThrowable(() -> {
            ExecutorResult<Domain<Long>> result = new CompletelyDeletingExecutorImpl<>(createFailRepository()).delete();
        });
        assertThat(throwable)
                .isInstanceOf(DTOException.class)
                .hasMessage("executor.deleting.completely.fail");
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
                = new CompletelyDeletingExecutorImpl<TestDomain, TestEntity>(createRepository()).delete();
        assertThat(result).isEqualTo(new DefaultExecutorResult<>());
    }

    private TestJpaRepository createRepository() {
        return Mockito.mock(TestJpaRepository.class);
    }
}