package kpn.financecontroller.data.services.dto.loaders;

import kpn.lib.domain.Domain;
import kpn.lib.entity.Entity;
import kpn.lib.exception.DTOException;
import kpn.lib.executor.DefaultExecutorResult;
import kpn.lib.executor.ExecutorResult;
import kpn.lib.executor.loading.ByIdLoadingExecutor;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.function.Function;

@AllArgsConstructor
public final class ByIdLoadingExecutorImpl<D extends Domain<Long>, E extends Entity<Long>> implements ByIdLoadingExecutor<Long, D> {
    private final JpaRepository<E, Long> repository;
    private final Function<E, D> converter;

    @Override
    public ExecutorResult<D> load(Long id) throws DTOException {
        try{
            Optional<E> maybeEntity = repository.findById(id);
            return maybeEntity.map(e -> new DefaultExecutorResult<>(converter.apply(e))).orElseGet(DefaultExecutorResult::new);
        } catch (Throwable t){
            throw new DTOException("executor.loading.byId.fail");
        }
    }
}
