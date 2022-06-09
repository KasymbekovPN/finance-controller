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
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
@AllArgsConstructor
final public class ByTagStatisticServiceImpl implements ByTagStatisticService<Task, Seed> {
    private static final int TASK_AMOUNT = 2;

    private final SizeChecker<Task[]> taskAmountChecker;
    private final OFunction<Task, ProductTask> productTaskChecker;
    private final OFunction<Task, PaymentTask> paymentTaskChecker;
    private final TaskExecutor<ProductTask, Product> productTaskExecutor;
    private final TaskExecutor<PaymentTask, Payment> paymentTaskExecutor;
    private final Function<Collection<Tag>, String> allTagNamesCreator;
    private final Function<Collection<Payment>, Float> totalPaymentCalculator;

    @Override
    public Seed calculate(Task... tasks) {
        if (!taskAmountChecker.check(tasks, TASK_AMOUNT)){
            return Codes.WRONG_TASKS_AMOUNT.createSeed(getServiceId());
        }

        Optional<ProductTask> maybeProductTask = productTaskChecker.apply(tasks[0]);
        if (maybeProductTask.isEmpty()){
            return Codes.WRONG_PRODUCT_TASK.createSeed(getServiceId());
        }

        Optional<PaymentTask> maybePaymentTask = paymentTaskChecker.apply(tasks[1]);
        if (maybePaymentTask.isEmpty()){
            return Codes.WRONG_PAYMENT_TASK.createSeed(getServiceId());
        }

        ProductTask productTask = maybeProductTask.get();
        Result<List<Product>> productResult = productTaskExecutor.execute(productTask);
        if (!productResult.isSuccess()){
            return Codes.WRONG_PRODUCT_RESULT.createSeed(getServiceId());
        }

        PaymentTask paymentTask = maybePaymentTask.get();
        paymentTask.setProducts(new HashSet<>(productResult.getValue()));

        Result<List<Payment>> paymentResult = paymentTaskExecutor.execute(paymentTask);
        if (!paymentResult.isSuccess()){
            return Codes.WRONG_PAYMENT_RESULT.createSeed(getServiceId());
        }

        ImmutableSeed.Builder builder = ImmutableSeed.builder().code(Results.getCode(productTask, paymentTask));
        if (!productTask.isAllTags()){
            builder.arg(allTagNamesCreator.apply(productTask.getTags()));
        }
        if (paymentTask.isBeginTimeEnable()){
            builder.arg(paymentTask.getBeginTime());
        }
        if (paymentTask.isEndTimeEnable()){
            builder.arg(paymentTask.getEndTime());
        }

        return builder
                .arg(totalPaymentCalculator.apply(paymentResult.getValue()))
                .build();
    }

    private String getServiceId() {
        return getClass().getSimpleName();
    }


    @RequiredArgsConstructor
    public enum Codes {
        WRONG_TASKS_AMOUNT("service.stat.byTag.wrongTasksAmount"),
        WRONG_PRODUCT_TASK("service.stat.byTag.wrongProductTask"),
        WRONG_PAYMENT_TASK("service.stat.byTag.wrongPaymentTask"),
        WRONG_PRODUCT_RESULT("service.stat.byTag.wrongProductResult"),
        WRONG_PAYMENT_RESULT("service.stat.byTag.wrongPaymentResult");

        @Getter
        private final String code;

        public Seed createSeed(String arg){
            return ImmutableSeed.builder()
                    .code(code)
                    .arg(arg)
                    .build();
        }
    }

    @RequiredArgsConstructor
    @Getter
    public enum Results {
        NO_BEGIN_NO_END("service.stat.byTag.result.noBeginNoEnd.byTags", "service.stat.byTag.result.noBeginNoEnd.allTags"),
        BEGIN_NO_END("service.stat.byTag.result.beginNoEnd.byTags", "service.stat.byTag.result.beginNoEnd.allTags"),
        NO_BEGIN_END("service.stat.byTag.result.noBeginEnd.byTags", "service.stat.byTag.result.noBeginEnd.allTags"),
        BEGIN_END("service.stat.byTag.result.beginEnd.byTags", "service.stat.byTag.result.beginEnd.allTags");

        private final String byTagsCode;
        private final String allTagsCode;

        public static String getCode(ProductTask productTask, PaymentTask paymentTask){
            int key = (paymentTask.isBeginTimeEnable() ? 1 : 0) + (paymentTask.isEndTimeEnable() ? 2 : 0);
            boolean allTags = productTask.isAllTags();
            return switch (key) {
                case 0 -> getConcreteCode(NO_BEGIN_NO_END, allTags);
                case 1 -> getConcreteCode(BEGIN_NO_END, allTags);
                case 2 -> getConcreteCode(NO_BEGIN_END, allTags);
                default -> getConcreteCode(BEGIN_END, allTags);
            };
        }

        private static String getConcreteCode(Results results, boolean allTags) {
            return allTags ? results.allTagsCode : results.byTagsCode;
        }
    }
}
