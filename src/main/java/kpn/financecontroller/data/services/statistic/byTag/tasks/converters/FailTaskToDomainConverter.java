package kpn.financecontroller.data.services.statistic.byTag.tasks.converters;

import kpn.financecontroller.data.services.statistic.byTag.tasks.task.Task;
import kpn.financecontroller.rfunc.RRFunction;
import kpn.lib.domain.Domain;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
final public class FailTaskToDomainConverter<T extends Task, D extends Domain<Long>> implements RRFunction<T, D> {
    @Override
    public Result<D> apply(Result<T> value) {
        ImmutableResult.Builder<D> builder = ImmutableResult.<D>builder()
                .success(value.isSuccess())
                .code(value.getSeed().getCode());
        Arrays.stream(value.getSeed().getArgs()).forEach(builder::arg);
        return builder.build();
    }
}
