package kpn.financecontroller.data.services.dto.executors;

import com.querydsl.core.types.Predicate;
import kpn.lib.domain.Domain;
import kpn.lib.entity.Entity;
import kpn.lib.exception.DTOException;
import kpn.lib.executor.DefaultExecutorResult;
import kpn.lib.executor.ExecutorResult;
import kpn.lib.executor.predicate.PredicateExecutor;
import lombok.AllArgsConstructor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
public final class PredicateExecutorImpl<D extends Domain<Long>, E extends Entity<Long>> implements PredicateExecutor<Predicate, D> {
    private final String executorId;
    private final QuerydslPredicateExecutor<E> repository;
    private final Function<E, D> converter;

    @Override
    public ExecutorResult<D> execute(Predicate predicate) throws DTOException {
        try{
            Iterable<E> it = repository.findAll(predicate);
            return new DefaultExecutorResult<>(
                    StreamSupport.stream(it.spliterator(), false)
                            .map(converter)
                            .collect(Collectors.toList())
            );
        } catch (Throwable t) {
            throw new DTOException("executor.predicate.fail", executorId);
        }
    }
}
