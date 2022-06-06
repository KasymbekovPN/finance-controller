package kpn.financecontroller.data.services.statistic.byTag;

import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.services.statistic.byTag.tasks.executor.TaskExecutor;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.PaymentTask;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.ProductTask;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.Task;
import kpn.financecontroller.rfunc.OFunction;
import kpn.financecontroller.rfunc.checker.size.SizeChecker;
import kpn.lib.result.Result;
import kpn.lib.seed.ImmutableSeed;
import kpn.lib.seed.Seed;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
final public class ByTagStatisticServiceImpl implements ByTagStatisticService<Task, Seed> {
    private static final int TASK_AMOUNT = 2;
    private static final String CODE__WRONG_TASKS_AMOUNT = "service.stat.byTag.wrongTasksAmount";
    private static final String CODE__WRONG_PRODUCT_TASK = "service.stat.byTag.wrongProductTask";
    private static final String CODE__WRONG_PAYMENT_TASK = "service.stat.byTag.wrongPaymentTask";
    private static final String CODE__WRONG_PRODUCT_RESULT = "service.stat.byTag.wrongProductResult";
    private static final String CODE__WRONG_PAYMENT_RESULT = "service.stat.byTag.wrongPaymentResult";
    private static final String CODE__RESULT_NO_BEGIN_NO_END = "service.stat.byTag.result.noBeginNoEnd";
    private static final String CODE__RESULT_BEGIN_NO_END = "service.stat.byTag.result.beginNoEnd";
    private static final String CODE__RESULT_NO_BEGIN_END = "service.stat.byTag.result.noBeginEnd";
    private static final String CODE__RESULT_BEGIN_END = "service.stat.byTag.result.beginEnd";

    private final SizeChecker<Task[]> taskAmountChecker;
    private final OFunction<Task, ProductTask> productTaskChecker;
    private final OFunction<Task, PaymentTask> paymentTaskChecker;
    private final TaskExecutor<ProductTask, Product> productTaskExecutor;
    private final TaskExecutor<PaymentTask, Payment> paymentTaskExecutor;

    @Override
    public Seed calculate(Task... tasks) {
        if (!taskAmountChecker.check(tasks, TASK_AMOUNT)){
            return createSeed(CODE__WRONG_TASKS_AMOUNT, getServiceId());
        }

        Optional<ProductTask> maybeProductTask = productTaskChecker.apply(tasks[0]);
        if (maybeProductTask.isEmpty()){
            return createSeed(CODE__WRONG_PRODUCT_TASK, getServiceId());
        }

        Optional<PaymentTask> maybePaymentTask = paymentTaskChecker.apply(tasks[1]);
        if (maybePaymentTask.isEmpty()){
            return createSeed(CODE__WRONG_PAYMENT_TASK, getServiceId());
        }

        Result<List<Product>> productResult = productTaskExecutor.execute(maybeProductTask.get());
        if (!productResult.isSuccess()){
            return createSeed(CODE__WRONG_PRODUCT_RESULT, getServiceId());
        }
        String tagNames = createTagNames(maybeProductTask.get());

        PaymentTask paymentTask = maybePaymentTask.get();
        paymentTask.setProducts(new HashSet<>(productResult.getValue()));

        Result<List<Payment>> paymentResult = paymentTaskExecutor.execute(paymentTask);
        if (!paymentResult.isSuccess()){
            return createSeed(CODE__WRONG_PAYMENT_RESULT, getServiceId());
        }

        // TODO: 06.06.2022 test it + should be bean
        double payment = calculatePayment(paymentResult.getValue());

        if (paymentTask.isBeginTimeEnable() && paymentTask.isEndTimeEnable()){
            return createSeed(CODE__RESULT_BEGIN_END, tagNames, paymentTask.getBeginTime(), paymentTask.getEndTime(), payment);
        }

        if (paymentTask.isBeginTimeEnable()){
            return createSeed(CODE__RESULT_BEGIN_NO_END, tagNames, paymentTask.getBeginTime(), payment);
        }

        if (paymentTask.isEndTimeEnable()){
            return createSeed(CODE__RESULT_NO_BEGIN_END, tagNames, paymentTask.getEndTime(), payment);
        }

        return createSeed(CODE__RESULT_NO_BEGIN_NO_END, tagNames, payment);
    }

    private double calculatePayment(List<Payment> value) {
        return value.stream()
                .map(p -> Double.valueOf(p.getPrice()))
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    // TODO: 06.06.2022 ?? bean
    private String createTagNames(ProductTask productTask) {
        // TODO: 06.06.2022 it's temp. solv.
        if (productTask.isAllTags()){
            return "all-tags";
        }

        StringBuilder tagNamesSB = new StringBuilder();
        String delimiter = "";
        for (Tag tag : productTask.getTags()) {
            tagNamesSB.append(delimiter).append(tag.getName());
            delimiter = ", ";
        }
        return tagNamesSB.toString();
    }

    private Seed createSeed(String code, Object... args) {
        ImmutableSeed.Builder builder = ImmutableSeed.builder()
                .code(code);
        Arrays.stream(args).forEach(builder::arg);
        return builder.build();
    }

    private String getServiceId() {
        return getClass().getSimpleName();
    }
}
