package kpn.financecontroller.data.services.statistic.byTag;

import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.services.statistic.byTag.tasks.executor.TaskExecutor;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.PaymentTask;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.ProductTask;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.Task;
import kpn.financecontroller.rfunc.OFunction;
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
    private static final int TASK_AMOUNT = 2;

    private final TaskExecutor<ProductTask, Product> productTaskExecutor;
    private final TaskExecutor<PaymentTask, Payment> paymentTaskExecutor;
    private final OFunction<Task, ProductTask> productTaskChecker;
    private final OFunction<Task, PaymentTask> paymentTaskChecker;

    @Override
    public Seed calculate(Task... tasks) {
        if (isWrongTaskSize(tasks)){
            return null;
        }

        Optional<ProductTask> maybeProductTask = productTaskChecker.apply(tasks[0]);
        if (maybeProductTask.isEmpty()){
            return null;
        }

        Optional<PaymentTask> maybePaymentTask = paymentTaskChecker.apply(tasks[1]);
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

    // TODO: 05.06.2022 replace with SizeChecker impl
    private boolean isWrongTaskSize(Task[] tasks) {
        return tasks.length != TASK_AMOUNT;
    }
}
