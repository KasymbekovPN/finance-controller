package kpn.financecontroller.data.services.statistic.byTag.tasks.converters;

import kpn.financecontroller.data.domains.Domain;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.Task;
import kpn.financecontroller.rfunc.RRFunction;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;

import java.util.Arrays;

final public class FailTaskToDomainConverter<TASK extends Task, DOMAIN extends Domain> implements RRFunction<TASK, DOMAIN> {
    @Override
    public Result<DOMAIN> apply(Result<TASK> value) {
        ImmutableResult.Builder<DOMAIN> builder = ImmutableResult.<DOMAIN>builder()
                .success(value.isSuccess())
                .code(value.getSeed().getCode());
        Arrays.stream(value.getSeed().getArgs()).forEach(builder::arg);
        return builder.build();
    }
}
