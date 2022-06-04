package kpn.financecontroller.data.services.statistic.byTag.tasks.checker;

import kpn.financecontroller.data.services.statistic.byTag.tasks.task.ProductTask;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.Task;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTaskTypeCheckerTest {

    @Test
    void shouldCheck_checkingOfWrongType() {
        Optional<ProductTask> result = new ProductTaskTypeChecker().apply(new TestTask());
        assertThat(result).isEmpty();
    }

    @Test
    void shouldCheck_checkingOfRightType() {
        ProductTask task = new ProductTask();
        Optional<ProductTask> result = new ProductTaskTypeChecker().apply(task);
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(task);
    }

    private static class TestTask implements Task {}
}