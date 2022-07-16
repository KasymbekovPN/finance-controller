package kpn.financecontroller.data.services.dto.savers;

import kpn.lib.domain.Domain;
import kpn.lib.entity.Entity;
import kpn.lib.exception.DTOException;
import kpn.lib.executor.DefaultExecutorResult;
import kpn.lib.executor.ExecutorResult;
import kpn.lib.executor.saving.SavingExecutor;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Function;

@AllArgsConstructor
public final class SavingExecutorImpl<D extends Domain<Long>, E extends Entity<Long>> implements SavingExecutor<D> {
    private final JpaRepository<E, Long> repository;
    private final Function<E, D> toDomainConverter;
    private final Function<D, E> toEntityConverter;

    @Override
    public ExecutorResult<D> save(D domain) throws DTOException {
        try{
            E savedEntity = repository.save(toEntityConverter.apply(domain));
            return new DefaultExecutorResult<>(toDomainConverter.apply(savedEntity));
        } catch (Throwable t){
            throw new DTOException("executor.saving.fail");
        }
    }
}
