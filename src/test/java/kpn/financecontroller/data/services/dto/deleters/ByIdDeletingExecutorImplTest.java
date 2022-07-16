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

class ByIdDeletingExecutorImplTest {

    @Test
    void shouldCheckDeleting_ifFail() {
        Throwable throwable = Assertions.catchThrowable(() -> {
            ExecutorResult<Domain<Long>> result = new ByIdDeletingExecutorImpl<>(createFailRepository()).delete(1L);
        });
        assertThat(throwable)
                .isInstanceOf(DTOException.class)
                .hasMessage("executor.deleting.byId.fail");
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
        ExecutorResult<TestDomain> result = new ByIdDeletingExecutorImpl<TestDomain, TestEntity>(createRepository()).delete(1L);
        assertThat(result).isEqualTo(new DefaultExecutorResult<>());
    }

    private TestJpaRepository createRepository() {
        return Mockito.mock(TestJpaRepository.class);
    }
}