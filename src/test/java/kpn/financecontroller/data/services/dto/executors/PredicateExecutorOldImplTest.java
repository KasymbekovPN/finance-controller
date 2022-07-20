// TODO: 20.07.2022 del
//package kpn.financecontroller.data.services.dto.executors;
//
//import com.querydsl.core.types.dsl.BooleanExpression;
//import kpn.financecontroller.data.services.dto.utils.QTestEntityOld;
//import kpn.financecontroller.data.services.dto.utils.TestEntityOld;
//import kpn.financecontroller.data.services.dto.utils.TestModelOld;
//import kpn.financecontroller.data.services.dto.utils.TestQuerydslPredicateExecutorOld;
//import kpn.lib.result.ImmutableResult;
//import kpn.lib.result.Result;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.mockito.exceptions.base.MockitoException;
//import org.springframework.data.querydsl.QuerydslPredicateExecutor;
//
//import java.util.List;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//// TODO: 16.07.2022 del
//class PredicateExecutorOldImplTest {
//
//    private static final String EXECUTOR_NAME = "some.executor";
//
//    private static BooleanExpression predicate;
//    private static List<TestEntityOld> returnedEntities;
//    private static ImmutableResult<List<TestModelOld>> expectedResultWhenExecutionFail;
//    private static ImmutableResult<List<TestModelOld>> expectedResult;
//
//    @BeforeAll
//    static void beforeAll() {
//        predicate = QTestEntityOld.testEntity.value.ne("");
//
//        List<TestModelOld> expectedDomains = List.of(new TestModelOld(1L, "name1"), new TestModelOld(2L, "name2"));
//        returnedEntities = expectedDomains.stream().map(TestEntityOld::new).collect(Collectors.toList());
//
//        expectedResultWhenExecutionFail = ImmutableResult.<List<TestModelOld>>bFail("executor.execution.fail")
//                .arg(EXECUTOR_NAME)
//                .build();
//        expectedResult = ImmutableResult.<List<TestModelOld>>bOk(expectedDomains).arg(EXECUTOR_NAME).build();
//    }
//
//    @Test
//    void shouldCheckExecution_whenExecutionFail() {
//        PredicateExecutorOldImpl<TestModelOld, TestEntityOld> executor
//                = new PredicateExecutorOldImpl<TestModelOld, TestEntityOld>(EXECUTOR_NAME, createFailExecutor(), null);
//        Result<List<TestModelOld>> result = executor.execute(predicate);
//        assertThat(expectedResultWhenExecutionFail).isEqualTo(result);
//    }
//
//    private QuerydslPredicateExecutor<TestEntityOld> createFailExecutor() {
//        TestQuerydslPredicateExecutorOld executor = Mockito.mock(TestQuerydslPredicateExecutorOld.class);
//        Mockito
//                .when(executor.findAll(predicate))
//                .thenThrow(new MockitoException(""));
//        return executor;
//    }
//
//    @Test
//    void shouldCheckExecution() {
//        PredicateExecutorOldImpl<TestModelOld, TestEntityOld> executor
//                = new PredicateExecutorOldImpl<TestModelOld, TestEntityOld>(EXECUTOR_NAME, createExecutor(), new TestConverter());
//        Result<List<TestModelOld>> result = executor.execute(predicate);
//        assertThat(expectedResult).isEqualTo(result);
//    }
//
//    private QuerydslPredicateExecutor<TestEntityOld> createExecutor() {
//        TestQuerydslPredicateExecutorOld executor = Mockito.mock(TestQuerydslPredicateExecutorOld.class);
//        Mockito
//                .when(executor.findAll(predicate))
//                .thenReturn(returnedEntities);
//        return executor;
//    }
//
//    private static class TestConverter implements Function<List<TestEntityOld>, List<TestModelOld>> {
//        @Override
//        public List<TestModelOld> apply(List<TestEntityOld> testEntities) {
//            return testEntities.stream().map(entity -> {
//                return new TestModelOld(entity.getId(), entity.getValue());
//            }).collect(Collectors.toList());
//        }
//    }
//}