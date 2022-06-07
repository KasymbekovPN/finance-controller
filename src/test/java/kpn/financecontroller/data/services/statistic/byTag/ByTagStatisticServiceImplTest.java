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
import kpn.lib.result.ImmutableResult;
import kpn.lib.seed.ImmutableSeed;
import kpn.lib.seed.Seed;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ByTagStatisticServiceImplTest {

    private static final Set<Long> TAG_IDS = Set.of(1L, 2L);

    private static ProductTask productTask;
    private static ArrayList<Payment> payments;
    private static float totalPrice;
    private static LocalDate beginTime = LocalDate.now();
    private static LocalDate endTime = LocalDate.now().plusDays(1);

    @BeforeAll
    static void beforeAll() {
        Set<Tag> tags = TAG_IDS.stream().map(id -> {
            Tag tag = new Tag();
            tag.setId(id);
            tag.setName(createTagName(id));
            return tag;
        }).collect(Collectors.toSet());

        productTask = new ProductTask();
        productTask.setTags(tags);

        payments = new ArrayList<>();
        Set<Float> prices = Set.of(123.45f, 234.56f, 345.67f);
        for (Float price : prices) {
            Payment payment = new Payment();
            payment.setPrice(price);
            payments.add(payment);
            totalPrice += price;
        }
    }

    private static String createTagName(Long id) {
        return "tag " + id;
    }

    // TODO: 06.06.2022 restore
//    @Test
//    void shouldCheckCalculation_whenWrongTaskSize() {
//        ImmutableSeed expectedResult = ImmutableSeed.builder()
//                .code("service.stat.byTag.wrongTasksAmount")
//                .arg("ByTagStatisticServiceImpl")
//                .build();
//
//        ByTagStatisticServiceImpl service = new ByTagStatisticServiceImpl(
//                createWrongTaskAmountChecker(),
//                null,
//                null,
//                null,
//                null
//        );
//        Seed seed = service.calculate(productTask, new PaymentTask());
//
//        assertThat(expectedResult).isEqualTo(seed);
//    }
//
//    @Test
//    void shouldCheckCalculation_whenProductTaskHasWrongType() {
//        ImmutableSeed expectedResult = ImmutableSeed.builder()
//                .code("service.stat.byTag.wrongProductTask")
//                .arg("ByTagStatisticServiceImpl")
//                .build();
//
//        ByTagStatisticServiceImpl service = new ByTagStatisticServiceImpl(
//                createTaskAmountChecker(),
//                createWrongProductTaskChecker(),
//                null,
//                null,
//                null
//        );
//        Seed seed = service.calculate(productTask, new PaymentTask());
//
//        assertThat(expectedResult).isEqualTo(seed);
//    }
//
//    @Test
//    void shouldCheckCalculation_whenPaymentTaskHasWrongType() {
//        ImmutableSeed expectedResult = ImmutableSeed.builder()
//                .code("service.stat.byTag.wrongPaymentTask")
//                .arg("ByTagStatisticServiceImpl")
//                .build();
//
//        ByTagStatisticServiceImpl service = new ByTagStatisticServiceImpl(
//                createTaskAmountChecker(),
//                createProductTaskChecker(),
//                createWrongPaymentTaskChecker(),
//                null,
//                null
//        );
//        Seed seed = service.calculate(productTask, new PaymentTask());
//
//        assertThat(expectedResult).isEqualTo(seed);
//    }
//
//    @Test
//    void shouldCheckCalculation_productExecutorReturnBadResult() {
//        ImmutableSeed expectedResult = ImmutableSeed.builder()
//                .code("service.stat.byTag.wrongProductResult")
//                .arg("ByTagStatisticServiceImpl")
//                .build();
//
//        PaymentTask paymentTask = createPaymentTask(null, null);
//        ByTagStatisticServiceImpl service = new ByTagStatisticServiceImpl(
//                createTaskAmountChecker(),
//                createProductTaskChecker(),
//                createPaymentTaskChecker(paymentTask),
//                createWrongProductExecutor(),
//                null
//        );
//        Seed seed = service.calculate(productTask, new PaymentTask());
//
//        assertThat(expectedResult).isEqualTo(seed);
//    }
//
//    @Test
//    void shouldCheckCalculation_paymentExecutorReturnBadResult() {
//        ImmutableSeed expectedResult = ImmutableSeed.builder()
//                .code("service.stat.byTag.wrongPaymentResult")
//                .arg("ByTagStatisticServiceImpl")
//                .build();
//
//        PaymentTask paymentTask = createPaymentTask(null, null);
//        ByTagStatisticServiceImpl service = new ByTagStatisticServiceImpl(
//                createTaskAmountChecker(),
//                createProductTaskChecker(),
//                createPaymentTaskChecker(paymentTask),
//                createProductExecutor(),
//                createWrongPaymentExecutor()
//        );
//        Seed seed = service.calculate(productTask, new PaymentTask());
//
//        assertThat(expectedResult).isEqualTo(seed);
//    }
//
//    @Test
//    void shouldCheckCalculation_noBegin_noEnd() {
//        String code = "service.stat.byTag.result.noBeginNoEnd";
//
//        PaymentTask paymentTask = createPaymentTask(null, null);
//        ByTagStatisticServiceImpl service = new ByTagStatisticServiceImpl(
//                createTaskAmountChecker(),
//                createProductTaskChecker(),
//                createPaymentTaskChecker(paymentTask),
//                createProductExecutor(),
//                createPaymentExecutor(payments)
//        );
//        Seed seed = service.calculate(productTask, paymentTask);
//
//        assertThat(seed).isNotNull();
//        assertThat(code).isEqualTo(seed.getCode());
//        assertThat(checkArgs((String) seed.getArgs()[0])).isTrue();
//    }
//
//    @Test
//    void shouldCheckCalculation_begin_noEnd() {
//        String code = "service.stat.byTag.result.beginNoEnd";
//
//        PaymentTask paymentTask = createPaymentTask(beginTime, null);
//        ByTagStatisticServiceImpl service = new ByTagStatisticServiceImpl(
//                createTaskAmountChecker(),
//                createProductTaskChecker(),
//                createPaymentTaskChecker(paymentTask),
//                createProductExecutor(),
//                createPaymentExecutor(payments)
//        );
//        Seed seed = service.calculate(productTask, paymentTask);
//
//        assertThat(seed).isNotNull();
//        assertThat(code).isEqualTo(seed.getCode());
//        assertThat(checkArgs((String) seed.getArgs()[0])).isTrue();
//        assertThat(beginTime).isEqualTo(seed.getArgs()[1]);
//    }
//
//    @Test
//    void shouldCheckCalculation_begin_End() {
//        String code = "service.stat.byTag.result.noBeginEnd";
//
//        PaymentTask paymentTask = createPaymentTask(null, endTime);
//        ByTagStatisticServiceImpl service = new ByTagStatisticServiceImpl(
//                createTaskAmountChecker(),
//                createProductTaskChecker(),
//                createPaymentTaskChecker(paymentTask),
//                createProductExecutor(),
//                createPaymentExecutor(payments)
//        );
//        Seed seed = service.calculate(productTask, paymentTask);
//
//        assertThat(seed).isNotNull();
//        assertThat(code).isEqualTo(seed.getCode());
//        assertThat(checkArgs((String) seed.getArgs()[0])).isTrue();
//        assertThat(endTime).isEqualTo(seed.getArgs()[1]);
//    }
//
//    @Test
//    void shouldCheckCalculation_begin_end() {
//        String code = "service.stat.byTag.result.beginEnd";
//
//        PaymentTask paymentTask = createPaymentTask(beginTime, endTime);
//        ByTagStatisticServiceImpl service = new ByTagStatisticServiceImpl(
//                createTaskAmountChecker(),
//                createProductTaskChecker(),
//                createPaymentTaskChecker(paymentTask),
//                createProductExecutor(),
//                createPaymentExecutor(payments)
//        );
//        Seed seed = service.calculate(productTask, paymentTask);
//
//        assertThat(seed).isNotNull();
//        assertThat(code).isEqualTo(seed.getCode());
//        assertThat(checkArgs((String) seed.getArgs()[0])).isTrue();
//        assertThat(beginTime).isEqualTo(seed.getArgs()[1]);
//        assertThat(endTime).isEqualTo(seed.getArgs()[2]);
//    }

    private SizeChecker<Task[]> createWrongTaskAmountChecker() {
        TestTaskAmountChecker checker = Mockito.mock(TestTaskAmountChecker.class);
        Mockito
                .when(checker.check(Mockito.anyObject(), Mockito.anyInt()))
                .thenReturn(false);
        return checker;
    }

    private SizeChecker<Task[]> createTaskAmountChecker() {
        TestTaskAmountChecker checker = Mockito.mock(TestTaskAmountChecker.class);
        Mockito
                .when(checker.check(Mockito.anyObject(), Mockito.anyInt()))
                .thenReturn(true);
        return checker;
    }

    private OFunction<Task, ProductTask> createWrongProductTaskChecker() {
        TestProductTaskChecker checker = Mockito.mock(TestProductTaskChecker.class);
        Mockito
                .when(checker.apply(Mockito.anyObject()))
                .thenReturn(Optional.empty());
        return checker;
    }

    private OFunction<Task, ProductTask> createProductTaskChecker() {
        TestProductTaskChecker checker = Mockito.mock(TestProductTaskChecker.class);
        Mockito
                .when(checker.apply(Mockito.anyObject()))
                .thenReturn(Optional.of(productTask));
        return checker;
    }

    private OFunction<Task, PaymentTask> createWrongPaymentTaskChecker() {
        TestPaymentTaskChecker checker = Mockito.mock(TestPaymentTaskChecker.class);
        Mockito
                .when(checker.apply(Mockito.anyObject()))
                .thenReturn(Optional.empty());
        return checker;
    }

    private OFunction<Task, PaymentTask> createPaymentTaskChecker(PaymentTask paymentTask) {
        TestPaymentTaskChecker checker = Mockito.mock(TestPaymentTaskChecker.class);
        Mockito
                .when(checker.apply(Mockito.anyObject()))
                .thenReturn(Optional.of(paymentTask));
        return checker;
    }

    private TaskExecutor<ProductTask, Product> createWrongProductExecutor() {
        TestProductExecutor executor = Mockito.mock(TestProductExecutor.class);
        Mockito
                .when(executor.execute(Mockito.anyObject()))
                .thenReturn(ImmutableResult.<List<Product>>fail(null));
        return executor;
    }

    private TaskExecutor<ProductTask, Product> createProductExecutor() {
        TestProductExecutor executor = Mockito.mock(TestProductExecutor.class);
        Mockito
                .when(executor.execute(Mockito.anyObject()))
                .thenReturn(ImmutableResult.<List<Product>>ok(List.of(new Product())));
        return executor;
    }

    private TaskExecutor<PaymentTask, Payment> createWrongPaymentExecutor() {
        TestPaymentExecutor executor = Mockito.mock(TestPaymentExecutor.class);
        Mockito
                .when(executor.execute(Mockito.anyObject()))
                .thenReturn(ImmutableResult.<List<Payment>>fail(null));
        return executor;
    }

    private TaskExecutor<PaymentTask, Payment> createPaymentExecutor(ArrayList<Payment> payments) {
        TestPaymentExecutor executor = Mockito.mock(TestPaymentExecutor.class);
        Mockito
                .when(executor.execute(Mockito.anyObject()))
                .thenReturn(ImmutableResult.<List<Payment>>ok(payments));
        return executor;
    }

    private PaymentTask createPaymentTask(LocalDate begin, LocalDate end) {
        PaymentTask task = new PaymentTask();
        if (begin != null){
            task.setBeginTimeEnable(true);
            task.setBeginTime(begin);
        }
        if (end != null){
            task.setEndTimeEnable(true);
            task.setEndTime(end);
        }
        return task;
    }

    private boolean checkArgs(String arg0) {
        Set<String> expectedTagNames = TAG_IDS.stream().map(ByTagStatisticServiceImplTest::createTagName).collect(Collectors.toSet());
        HashSet<String> tagNames = new HashSet<>(List.of(arg0.split(", ")));
        return expectedTagNames.equals(tagNames);
    }

    abstract private static class TestTaskAmountChecker implements SizeChecker<Task[]>{}
    abstract private static class TestProductTaskChecker implements OFunction<Task, ProductTask>{}
    abstract private static class TestPaymentTaskChecker implements OFunction<Task, PaymentTask>{}
    abstract private static class TestProductExecutor implements TaskExecutor<ProductTask, Product> {}
    abstract private static class TestPaymentExecutor implements TaskExecutor<PaymentTask, Payment> {}
}