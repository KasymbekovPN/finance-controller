package kpn.financecontroller.data.services.statistic.byTag.tasks.checker;

import kpn.financecontroller.data.services.statistic.byTag.tasks.task.PaymentTask;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.Task;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentTaskTypeCheckerTest {

    @Test
    void shouldCheck_checkingOfWrongType() {
        Optional<PaymentTask> result = new PaymentTaskTypeChecker().apply(new TestTask());
        assertThat(result).isEmpty();
    }

    @Test
    void shouldCheck_checkingOfRightType() {
        PaymentTask task = new PaymentTask();
        Optional<PaymentTask> result = new PaymentTaskTypeChecker().apply(task);
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(task);
    }

    private static class TestTask implements Task {}
}