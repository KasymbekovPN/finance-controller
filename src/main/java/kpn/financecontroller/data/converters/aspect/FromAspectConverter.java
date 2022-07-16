package kpn.financecontroller.data.converters.aspect;

import kpn.lib.aspect.AspectResult;
import kpn.lib.domain.Domain;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public final class FromAspectConverter<D extends Domain<Long>> implements Function<AspectResult<D>, Result<List<D>>> {

    @Override
    public Result<List<D>> apply(AspectResult<D> aspectResult) {
        if (aspectResult.isSuccess()){
            ArrayList<D> list = new ArrayList<>();
            aspectResult.getExecutorResult().iterator().forEachRemaining(list::add);
            return ImmutableResult.<List<D>>ok(list);
        }

        ImmutableResult.Builder<List<D>> builder = ImmutableResult.<List<D>>builder()
                .code(aspectResult.getCode());
        Arrays.stream(aspectResult.getArgs()).forEach(builder::arg);

        return builder.build();
    }
}
