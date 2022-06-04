package kpn.financecontroller.data.services.statistic.byTag.tasks.checker;

import kpn.financecontroller.data.services.statistic.byTag.tasks.task.ProductTask;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.Task;
import kpn.financecontroller.rfunc.OFunction;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
final public class ProductTaskTypeChecker implements OFunction<Task, ProductTask> {

    @Override
    public Optional<ProductTask> apply(Task value) {
        return value.getClass().equals(ProductTask.class)
                ? Optional.of((ProductTask) value)
                : Optional.empty();
    }
}
