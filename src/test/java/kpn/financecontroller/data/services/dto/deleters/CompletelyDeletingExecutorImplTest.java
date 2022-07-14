package kpn.financecontroller.data.services.dto.deleters;

import kpn.lib.domain.AbstractDomain;
import kpn.lib.domain.Domain;
import kpn.lib.entity.AbstractEntity;
import kpn.lib.exception.DTOException;
import kpn.lib.executor.DefaultExecutorResult;
import kpn.lib.executor.ExecutorResult;
import lombok.Getter;
import lombok.Setter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.data.jpa.repository.JpaRepository;

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

    // TODO: 14.07.2022 move to file
    private abstract static class TestJpaRepository implements JpaRepository<TestEntity, Long>{}

    // TODO: 14.07.2022 move to file
    @Getter
    @Setter
    private static class TestEntity extends AbstractEntity<Long>{
        private Long id;
    }

    // TODO: 14.07.2022 move to file
    private static class TestDomain extends AbstractDomain<Long>{}
}