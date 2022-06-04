package kpn.financecontroller.data.services.statistic.byTag;

import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.services.statistic.byTag.tasks.executor.TaskExecutor;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.PaymentTask;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.ProductTask;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.Task;
import kpn.lib.result.Result;
import kpn.lib.seed.Seed;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
final public class ByTagStatisticServiceImpl implements ByTagStatisticService<Task, Seed> {
    private final TaskExecutor<ProductTask, Product> productTaskExecutor;
    private final TaskExecutor<PaymentTask, Payment> paymentTaskExecutor;

    @Override
    public Seed calculate(Task... tasks) {
        if (isWrongTaskSize(tasks)){
            return null;
        }

        Optional<ProductTask> maybeProductTask = checkProductTask(tasks[0]);
        if (maybeProductTask.isEmpty()){
            return null;
        }

        Optional<PaymentTask> maybePaymentTask = checkPaymentTask(tasks[1]);
        if (maybePaymentTask.isEmpty()){
            return null;
        }

        Result<List<Product>> productResult = productTaskExecutor.execute(maybeProductTask.get());
        if (!productResult.isSuccess()){
            return null;
        }

        PaymentTask paymentTask = maybePaymentTask.get();
        paymentTask.setProducts(new HashSet<>(productResult.getValue()));
        // TODO: 04.06.2022 restore
//        return paymentTaskExecutor.execute(paymentTask);

        return null;
    }

    private boolean isWrongTaskSize(Task[] tasks) {
        return false;
    }

    private Optional<ProductTask> checkProductTask(Task task) {
        return null;
    }

    private Optional<PaymentTask> checkPaymentTask(Task task) {
        return null;
    }
}
