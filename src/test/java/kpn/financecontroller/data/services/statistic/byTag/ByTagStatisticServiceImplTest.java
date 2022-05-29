package kpn.financecontroller.data.services.statistic.byTag;

import static org.assertj.core.api.Assertions.assertThat;

class ByTagStatisticServiceImplTest {

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