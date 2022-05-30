package kpn.financecontroller.data.services.statistic.byTag.tasks.converters;

import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.ProductTask;
import kpn.financecontroller.rfunc.RRFunction;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;

import java.util.Arrays;

final public class FailTaskToProductRRConverter implements RRFunction<ProductTask, Product> {

    @Override
    public Result<Product> apply(Result<ProductTask> value) {
        ImmutableResult.Builder<Product> builder = ImmutableResult.<Product>builder()
                .success(value.isSuccess())
                .code(value.getSeed().getCode());
        Arrays.stream(value.getSeed().getArgs()).forEach(builder::arg);
        return builder.build();
    }
}
