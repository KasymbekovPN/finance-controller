package kpn.financecontroller.data.services.statistic.byTag;

import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.services.statistic.byTag.tasks.executor.TaskExecutor;
import kpn.financecontroller.data.services.statistic.byTag.tasks.task.ProductTask;
import kpn.lib.result.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class ByTagStatisticServiceImplTest {

    // TODO: 05.06.2022 del
//    @BeforeEach
//    void setUp() {
//        new ByTagStatisticServiceImpl(
//                createProductTaskExecutor(),
//                createPaymentTaskExecutor(),
//                createProductTaskChecker(),
//                createPaymentTaskChecker()
//        )
//    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckCalculation_whenWrongTaskSize.csv")
    void shouldCheckCalculation_whenWrongTaskSize(int size, boolean expectedResult, String expectedCode) {
    }

    @Test
    void shouldCheckCalculation_whenProductTaskHasWrongType() {

    }

    @Test
    void shouldCheckCalculation_whenPaymentTaskHasWrongType() {

    }

    @Test
    void shouldCheckCalculation_productExecutorReturnBadResult() {

    }

    @Test
    void shouldCheckCalculation_paymentExecutorReturnBadResult() {

    }

    @Test
    void shouldCheckCalculation() {

    }

    // TODO: 04.06.2022 del
//    private static final String FAIL_CHECKING_CODE = "fail.checking.code";
//    private static final String FAIL_CHECKING_ARG = "fail.checking.arg";
//
//    private static ImmutableSeed expectedSeedWhenCheckingFail;
//    private static ImmutableResult<Void> expectedResultWhenCheckingFail;
//    private static ImmutableResult<Void> expectedResultWhenCheckingSuccess;
//
//    @BeforeAll
//    static void beforeAll() {
//        expectedSeedWhenCheckingFail = ImmutableSeed.builder().code(FAIL_CHECKING_CODE).arg(FAIL_CHECKING_ARG).build();
//        expectedResultWhenCheckingFail = ImmutableResult.<Void>bFail(FAIL_CHECKING_CODE).arg(FAIL_CHECKING_ARG).build();
//
//        expectedResultWhenCheckingSuccess = ImmutableResult.<Void>ok(null);
//    }
//
//    @Test
//    void shouldCheckCalculation_whenQueryCheckingFail() {
//        ByTagStatisticServiceImpl service = new ByTagStatisticServiceImpl( createFailChecker());
//        Seed seed = service.calculate(new Query());
//
//        assertThat(expectedSeedWhenCheckingFail).isEqualTo(seed);
//    }
//
//    private Function<Query, Result<Void>> createFailChecker(){
//        TestChecker checker = Mockito.mock(TestChecker.class);
//        Mockito
//                .when(checker.apply(Mockito.any(Query.class)))
//                .thenReturn(expectedResultWhenCheckingFail);
//        return checker;
//    }
//
//    @Test
//    void shouldCheckCalculation_whenNoOneEntities() {
//
//    }
//
//    private Function<Query, Result<Void>> createChecker(){
//        TestChecker checker = Mockito.mock(TestChecker.class);
//        Mockito
//                .when(checker.apply(Mockito.any(Query.class)))
//                .thenReturn(expectedResultWhenCheckingSuccess);
//        return checker;
//    }
//
//    // == 0 - entities
//    // != 0 - entities
//
//    private interface TestChecker extends Function<Query, Result<Void>>{}
//    private interface TestDtoService extends DTOService<Payment, PaymentEntity, Long>{}
}