package kpn.financecontroller.data.services.dto.deleters;

import kpn.lib.domain.Domain;
import kpn.lib.entity.Entity;
import kpn.lib.exception.DTOException;
import kpn.lib.executor.DefaultExecutorResult;
import kpn.lib.executor.ExecutorResult;
import kpn.lib.executor.deleting.ByIdDeletingExecutor;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

@AllArgsConstructor
public final class ByIdDeletingExecutorImpl<D extends Domain<Long>, E extends Entity<Long>> implements ByIdDeletingExecutor<Long, D> {
    private final JpaRepository<E, Long> repository;

    @Override
    public ExecutorResult<D> delete(Long id) throws DTOException {
        try{
            repository.deleteById(id);
            return new DefaultExecutorResult<>();
        } catch (Throwable t){
            throw new DTOException("executor.deleting.byId.fail");
        }
    }
}
