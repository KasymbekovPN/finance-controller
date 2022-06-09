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
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ByTagStatisticServiceImplTest {

    private static final Set<Long> TAG_IDS = Set.of(1L, 2L);
    private static final String CLASS_NAME = ByTagStatisticServiceImpl.class.getSimpleName();
    private static final String NAMES = "names";
    private static final Float TOTAL_PAYMENT = 1234.56f;
    private static final LocalDate BEGIN_TIME = LocalDate.now();
    private static final LocalDate END_TIME = LocalDate.now().plusDays(1);

    private static ProductTask productTaskByTags;
    private static ProductTask productTaskAllTags;
    private static ArrayList<Payment> payments;

    @BeforeAll
    static void beforeAll() {
        Set<Tag> tags = TAG_IDS.stream().map(id -> {
            Tag tag = new Tag();
            tag.setId(id);
            tag.setName(createTagName(id));
            return tag;
        }).collect(Collectors.toSet());

        productTaskByTags = new ProductTask();
        productTaskByTags.setTags(tags);

        productTaskAllTags = new ProductTask();
        productTaskAllTags.setAllTags(true);

        payments = new ArrayList<>();
        Set<Float> prices = Set.of(123.45f, 234.56f, 345.67f);
        for (Float price : prices) {
            Payment payment = new Payment();
            payment.setPrice(price);
            payments.add(payment);
        }
    }

    private static String createTagName(Long id) {
        return "tag " + id;
    }

    @Test
    void shouldCheckCalculation_whenWrongTaskSize() {
        ImmutableSeed expectedSeed = ImmutableSeed.builder()
                .code(ByTagStatisticServiceImpl.Codes.WRONG_TASKS_AMOUNT.getCode())
                .arg(CLASS_NAME)
                .build();

        ByTagStatisticServiceImpl service = new ByTagStatisticServiceImpl(
                createWrongTaskAmountChecker(),
                null,
                null,
                null,
                null,
                null,
                null
        );
        Seed seed = service.calculate(productTaskByTags, new PaymentTask());

        assertThat(expectedSeed).isEqualTo(seed);
    }

    @Test
    void shouldCheckCalculation_whenProductTaskHasWrongType() {
        ImmutableSeed expectedResult = ImmutableSeed.builder()
                .code(ByTagStatisticServiceImpl.Codes.WRONG_PRODUCT_TASK.getCode())
                .arg(CLASS_NAME)
                .build();

        ByTagStatisticServiceImpl service = new ByTagStatisticServiceImpl(
                createTaskAmountChecker(),
                createWrongProductTaskChecker(),
                null,
                null,
                null,
                null,
                null
        );
        Seed seed = service.calculate(productTaskByTags, new PaymentTask());

        assertThat(expectedResult).isEqualTo(seed);
    }

    @Test
    void shouldCheckCalculation_whenPaymentTaskHasWrongType() {
        ImmutableSeed expectedResult = ImmutableSeed.builder()
                .code(ByTagStatisticServiceImpl.Codes.WRONG_PAYMENT_TASK.getCode())
                .arg(CLASS_NAME)
                .build();

        ByTagStatisticServiceImpl service = new ByTagStatisticServiceImpl(
                createTaskAmountChecker(),
                createProductTaskChecker(productTaskByTags),
                createWrongPaymentTaskChecker(),
                null,
                null,
                null,
                null
        );
        Seed seed = service.calculate(productTaskByTags, new PaymentTask());

        assertThat(expectedResult).isEqualTo(seed);
    }

    @Test
    void shouldCheckCalculation_productExecutorReturnBadResult() {
        ImmutableSeed expectedResult = ImmutableSeed.builder()
                .code(ByTagStatisticServiceImpl.Codes.WRONG_PRODUCT_RESULT.getCode())
                .arg(CLASS_NAME)
                .build();

        PaymentTask paymentTask = createPaymentTask(null, null);
        ByTagStatisticServiceImpl service = new ByTagStatisticServiceImpl(
                createTaskAmountChecker(),
                createProductTaskChecker(productTaskByTags),
                createPaymentTaskChecker(paymentTask),
                createWrongProductExecutor(),
                null,
                null,
                null
        );
        Seed seed = service.calculate(productTaskByTags, new PaymentTask());

        assertThat(expectedResult).isEqualTo(seed);
    }

    @Test
    void shouldCheckCalculation_paymentExecutorReturnBadResult() {
        ImmutableSeed expectedResult = ImmutableSeed.builder()
                .code(ByTagStatisticServiceImpl.Codes.WRONG_PAYMENT_RESULT.getCode())
                .arg(CLASS_NAME)
                .build();

        PaymentTask paymentTask = createPaymentTask(null, null);
        ByTagStatisticServiceImpl service = new ByTagStatisticServiceImpl(
                createTaskAmountChecker(),
                createProductTaskChecker(productTaskByTags),
                createPaymentTaskChecker(paymentTask),
                createProductExecutor(),
                createWrongPaymentExecutor(),
                null,
                null
        );
        Seed seed = service.calculate(productTaskByTags, new PaymentTask());

        assertThat(expectedResult).isEqualTo(seed);
    }

    @Test
    void shouldCheckCalculation_noBegin_noEnd_byTags() {
        PaymentTask paymentTask = createPaymentTask(null, null);
        ByTagStatisticServiceImpl service = new ByTagStatisticServiceImpl(
                createTaskAmountChecker(),
                createProductTaskChecker(productTaskByTags),
                createPaymentTaskChecker(paymentTask),
                createProductExecutor(),
                createPaymentExecutor(payments),
                createNamesCalculator(),
                createPaymentCalculator()
        );
        Seed seed = service.calculate(productTaskByTags, paymentTask);

        ImmutableSeed expectedSeed = ImmutableSeed.builder()
                .code(ByTagStatisticServiceImpl.Results.NO_BEGIN_NO_END.getByTagsCode())
                .arg(NAMES)
                .arg(TOTAL_PAYMENT)
                .build();
        assertThat(expectedSeed).isEqualTo(seed);
    }

    @Test
    void shouldCheckCalculation_noBegin_noEnd_allTags() {
        PaymentTask paymentTask = createPaymentTask(null, null);
        ByTagStatisticServiceImpl service = new ByTagStatisticServiceImpl(
                createTaskAmountChecker(),
                createProductTaskChecker(productTaskAllTags),
                createPaymentTaskChecker(paymentTask),
                createProductExecutor(),
                createPaymentExecutor(payments),
                createNamesCalculator(),
                createPaymentCalculator()
        );
        Seed seed = service.calculate(productTaskAllTags, paymentTask);

        ImmutableSeed expectedSeed = ImmutableSeed.builder()
                .code(ByTagStatisticServiceImpl.Results.NO_BEGIN_NO_END.getAllTagsCode())
                .arg(TOTAL_PAYMENT)
                .build();
        assertThat(expectedSeed).isEqualTo(seed);
    }

    @Test
    void shouldCheckCalculation_begin_noEnd_byTags() {
        PaymentTask paymentTask = createPaymentTask(BEGIN_TIME, null);
        ByTagStatisticServiceImpl service = new ByTagStatisticServiceImpl(
                createTaskAmountChecker(),
                createProductTaskChecker(productTaskByTags),
                createPaymentTaskChecker(paymentTask),
                createProductExecutor(),
                createPaymentExecutor(payments),
                createNamesCalculator(),
                createPaymentCalculator()
        );
        Seed seed = service.calculate(productTaskByTags, paymentTask);

        ImmutableSeed expectedSeed = ImmutableSeed.builder()
                .code(ByTagStatisticServiceImpl.Results.BEGIN_NO_END.getByTagsCode())
                .arg(NAMES)
                .arg(BEGIN_TIME)
                .arg(TOTAL_PAYMENT)
                .build();
        assertThat(expectedSeed).isEqualTo(seed);
    }

    @Test
    void shouldCheckCalculation_begin_noEnd_allTags() {
        PaymentTask paymentTask = createPaymentTask(BEGIN_TIME, null);
        ByTagStatisticServiceImpl service = new ByTagStatisticServiceImpl(
                createTaskAmountChecker(),
                createProductTaskChecker(productTaskAllTags),
                createPaymentTaskChecker(paymentTask),
                createProductExecutor(),
                createPaymentExecutor(payments),
                createNamesCalculator(),
                createPaymentCalculator()
        );
        Seed seed = service.calculate(productTaskAllTags, paymentTask);

        ImmutableSeed expectedSeed = ImmutableSeed.builder()
                .code(ByTagStatisticServiceImpl.Results.BEGIN_NO_END.getAllTagsCode())
                .arg(BEGIN_TIME)
                .arg(TOTAL_PAYMENT)
                .build();
        assertThat(expectedSeed).isEqualTo(seed);
    }

    @Test
    void shouldCheckCalculation_noBegin_end_byTags() {
        PaymentTask paymentTask = createPaymentTask(null, END_TIME);
        ByTagStatisticServiceImpl service = new ByTagStatisticServiceImpl(
                createTaskAmountChecker(),
                createProductTaskChecker(productTaskByTags),
                createPaymentTaskChecker(paymentTask),
                createProductExecutor(),
                createPaymentExecutor(payments),
                createNamesCalculator(),
                createPaymentCalculator()
        );
        Seed seed = service.calculate(productTaskByTags, paymentTask);

        ImmutableSeed expectedSeed = ImmutableSeed.builder()
                .code(ByTagStatisticServiceImpl.Results.NO_BEGIN_END.getByTagsCode())
                .arg(NAMES)
                .arg(END_TIME)
                .arg(TOTAL_PAYMENT)
                .build();
        assertThat(expectedSeed).isEqualTo(seed);
    }

    @Test
    void shouldCheckCalculation_noBegin_end_allTags() {
        PaymentTask paymentTask = createPaymentTask(null, END_TIME);
        ByTagStatisticServiceImpl service = new ByTagStatisticServiceImpl(
                createTaskAmountChecker(),
                createProductTaskChecker(productTaskAllTags),
                createPaymentTaskChecker(paymentTask),
                createProductExecutor(),
                createPaymentExecutor(payments),
                createNamesCalculator(),
                createPaymentCalculator()
        );
        Seed seed = service.calculate(productTaskAllTags, paymentTask);

        ImmutableSeed expectedSeed = ImmutableSeed.builder()
                .code(ByTagStatisticServiceImpl.Results.NO_BEGIN_END.getAllTagsCode())
                .arg(END_TIME)
                .arg(TOTAL_PAYMENT)
                .build();
        assertThat(expectedSeed).isEqualTo(seed);
    }

    @Test
    void shouldCheckCalculation_begin_end_byTags() {
        PaymentTask paymentTask = createPaymentTask(BEGIN_TIME, END_TIME);
        ByTagStatisticServiceImpl service = new ByTagStatisticServiceImpl(
                createTaskAmountChecker(),
                createProductTaskChecker(productTaskByTags),
                createPaymentTaskChecker(paymentTask),
                createProductExecutor(),
                createPaymentExecutor(payments),
                createNamesCalculator(),
                createPaymentCalculator()
        );
        Seed seed = service.calculate(productTaskByTags, paymentTask);

        ImmutableSeed expectedSeed = ImmutableSeed.builder()
                .code(ByTagStatisticServiceImpl.Results.BEGIN_END.getByTagsCode())
                .arg(NAMES)
                .arg(BEGIN_TIME)
                .arg(END_TIME)
                .arg(TOTAL_PAYMENT)
                .build();
        assertThat(expectedSeed).isEqualTo(seed);
    }

    @Test
    void shouldCheckCalculation_begin_end_allTags() {
        PaymentTask paymentTask = createPaymentTask(BEGIN_TIME, END_TIME);
        ByTagStatisticServiceImpl service = new ByTagStatisticServiceImpl(
                createTaskAmountChecker(),
                createProductTaskChecker(productTaskAllTags),
                createPaymentTaskChecker(paymentTask),
                createProductExecutor(),
                createPaymentExecutor(payments),
                createNamesCalculator(),
                createPaymentCalculator()
        );
        Seed seed = service.calculate(productTaskAllTags, paymentTask);

        ImmutableSeed expectedSeed = ImmutableSeed.builder()
                .code(ByTagStatisticServiceImpl.Results.BEGIN_END.getAllTagsCode())
                .arg(BEGIN_TIME)
                .arg(END_TIME)
                .arg(TOTAL_PAYMENT)
                .build();
        assertThat(expectedSeed).isEqualTo(seed);
    }

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

    private OFunction<Task, ProductTask> createProductTaskChecker(ProductTask productTask) {
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

    private Function<Collection<Tag>, String> createNamesCalculator() {
        TestNamesCalculator calculator = Mockito.mock(TestNamesCalculator.class);
        Mockito
                .when(calculator.apply(Mockito.anyObject()))
                .thenReturn(NAMES);
        return calculator;
    }

    private Function<Collection<Payment>, Float> createPaymentCalculator() {
        TestPaymentCalculator calculator = Mockito.mock(TestPaymentCalculator.class);
        Mockito
                .when(calculator.apply(Mockito.anyObject()))
                .thenReturn(TOTAL_PAYMENT);
        return calculator;
    }

    abstract private static class TestTaskAmountChecker implements SizeChecker<Task[]>{}
    abstract private static class TestProductTaskChecker implements OFunction<Task, ProductTask>{}
    abstract private static class TestPaymentTaskChecker implements OFunction<Task, PaymentTask>{}
    abstract private static class TestProductExecutor implements TaskExecutor<ProductTask, Product> {}
    abstract private static class TestPaymentExecutor implements TaskExecutor<PaymentTask, Payment> {}
    abstract private static class TestNamesCalculator implements Function<Collection<Tag>, String>{}
    abstract private static class TestPaymentCalculator implements Function<Collection<Payment>, Float>{}
}