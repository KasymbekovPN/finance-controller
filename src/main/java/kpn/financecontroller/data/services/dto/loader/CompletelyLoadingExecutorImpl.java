package kpn.financecontroller.data.services.dto.loader;

import kpn.lib.domain.Domain;
import kpn.lib.entity.Entity;
import kpn.lib.exception.DTOException;
import kpn.lib.executor.DefaultExecutorResult;
import kpn.lib.executor.ExecutorResult;
import kpn.lib.executor.loading.CompletelyLoadingExecutor;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
public final class CompletelyLoadingExecutorImpl<D extends Domain<Long>, E extends Entity<Long>> implements CompletelyLoadingExecutor<D> {
    private final String executorId;
    private final JpaRepository<E, Long> repository;
    private final Function<E, D> converter;

    @Override
    public ExecutorResult<D> load() throws DTOException {
        try {
            List<D> domains = repository.findAll().stream().map(converter).collect(Collectors.toList());
            return new DefaultExecutorResult<>(domains);
        } catch (Throwable t){
            throw new DTOException("executor.loading.completely.fail", executorId);
        }
    }
}
