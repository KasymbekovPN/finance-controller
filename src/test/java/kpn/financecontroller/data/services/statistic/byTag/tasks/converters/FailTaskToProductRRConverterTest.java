package kpn.financecontroller.data.services.statistic.byTag.tasks.converters;

import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.ProductTask;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class FailTaskToProductRRConverterTest {

    private static final boolean SUCCESS = true;
    private static final String CODE = "some.code";
    private static final Object[] ARGS = new Object[]{1, 2, 3};

    @Test
    void shouldCheckConversion() {
        Result<ProductTask> productTaskResult = createProductTaskResult();
        Result<Product> productResult = new FailTaskToProductRRConverter().apply(productTaskResult);

        assertThat(productResult.isSuccess()).isEqualTo(productTaskResult.isSuccess());
        assertThat(productResult.getValue()).isNull();
        assertThat(productResult.getSeed().getCode()).isEqualTo(productTaskResult.getSeed().getCode());
        assertThat(productResult.getSeed().getArgs()).isEqualTo(productTaskResult.getSeed().getArgs());
    }

    private Result<ProductTask> createProductTaskResult() {
        ImmutableResult.Builder<ProductTask> builder = ImmutableResult.<ProductTask>builder()
                .success(SUCCESS)
                .value(new ProductTask())
                .code(CODE);
        Arrays.stream(ARGS).forEach(builder::arg);
        return builder.build();
    }
}