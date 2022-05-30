package kpn.financecontroller.data.services.statistic.byTag.tasks.converters;

import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.PaymentTask;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class FailTaskToPaymentRRConverterTest {

    private static final boolean SUCCESS = true;
    private static final String CODE = "some.code";
    private static final Object[] ARGS = new Object[]{1, 2, 3};

    @Test
    void shouldCheckConversion() {
        Result<PaymentTask> paymentTaskResult = createPaymentTaskResult();
        Result<Payment> paymentResult = new FailTaskToPaymentRRConverter().apply(paymentTaskResult);

        assertThat(paymentResult.isSuccess()).isEqualTo(paymentTaskResult.isSuccess());
        assertThat(paymentResult.getValue()).isNull();
        assertThat(paymentResult.getSeed().getCode()).isEqualTo(paymentTaskResult.getSeed().getCode());
        assertThat(paymentResult.getSeed().getArgs()).isEqualTo(paymentTaskResult.getSeed().getArgs());
    }

    private Result<PaymentTask> createPaymentTaskResult() {
        ImmutableResult.Builder<PaymentTask> builder = ImmutableResult.<PaymentTask>builder()
                .success(SUCCESS)
                .value(new PaymentTask())
                .code(CODE);
        Arrays.stream(ARGS).forEach(builder::arg);
        return builder.build();
    }
}