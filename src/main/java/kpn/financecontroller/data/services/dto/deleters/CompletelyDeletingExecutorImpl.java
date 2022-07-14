package kpn.financecontroller.data.services.dto.deleters;

import kpn.lib.domain.Domain;
import kpn.lib.entity.Entity;
import kpn.lib.exception.DTOException;
import kpn.lib.executor.DefaultExecutorResult;
import kpn.lib.executor.ExecutorResult;
import kpn.lib.executor.deleting.CompletelyDeletingExecutor;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

@AllArgsConstructor
public final class CompletelyDeletingExecutorImpl<D extends Domain<Long>, E extends Entity<Long>> implements CompletelyDeletingExecutor<D> {
    private final JpaRepository<E, Long> repository;

    @Override
    public ExecutorResult<D> delete() throws DTOException {
        try{
            repository.deleteAll();
            return new DefaultExecutorResult<>();
        } catch (Throwable t){
            throw new DTOException("executor.deleting.completely.fail");
        }
    }
}
