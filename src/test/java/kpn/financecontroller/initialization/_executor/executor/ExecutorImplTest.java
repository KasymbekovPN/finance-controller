// TODO: 16.03.2022  del
//package kpn.financecontroller.initialization._executor.executor;
//
//import kpn.financecontroller.initialization._context.context.Context;
//import kpn.financecontroller.initialization._context.context.ContextImpl;
//import kpn.financecontroller.initialization._factory.factory.TaskFactory;
//import kpn.financecontroller.initialization._task.task.Task;
//import kpn.financecontroller.result.Result;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayDeque;
//import java.util.Deque;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class ExecutorImplTest {
//
//    private static final String TEST_FACTORY = "test.factory";
//    private static final String TERMINATE_KEY = "terminate.key";
//    private static final String RESULT_PROPERTY = "result.property";
//
//    private static Result<Void> expectedResultWhenTerminated;
//    private static Result<Void> expectedResult;
//
//    @BeforeAll
//    static void beforeAll() {
//        expectedResultWhenTerminated = Result.<Void>builder()
//                .success(false)
//                .code("executor.terminated")
//                .arg(TEST_FACTORY)
//                .arg(TERMINATE_KEY)
//                .build();
//        expectedResult = Result.<Void>builder()
//                .success(true)
//                .code("executor.success")
//                .arg(TEST_FACTORY)
//                .build();
//    }
//
//    @Test
//    void shouldCheckExecution_withTermination() {
//        ContextImpl context = new ContextImpl();
//        ExecutorImpl executor = new ExecutorImpl(context);
//
//        Result<Void> result = executor.execute(new TestTaskFactory(List.of("key0", "key1", TERMINATE_KEY, "key2")));
//        assertThat(expectedResultWhenTerminated).isEqualTo(result);
//    }
//
//    @Test
//    void shouldCheckExecution() {
//        ContextImpl context = new ContextImpl();
//        ExecutorImpl executor = new ExecutorImpl(context);
//
//        List<String> keys = List.of("key0", "key1", "key2");
//        Result<Void> result = executor.execute(new TestTaskFactory(keys));
//        assertThat(expectedResult).isEqualTo(result);
//
//        for (String key : keys) {
//            Optional<Object> maybeObject = context.get(key, RESULT_PROPERTY);
//            assertThat(maybeObject).isPresent();
//        }
//    }
//
//    private static class TestTaskFactory implements TaskFactory{
//        private final Deque<String> keys;
//
//        public TestTaskFactory(List<String> keys) {
//            this.keys = new ArrayDeque<>(keys);
//        }
//
//        @Override
//        public Optional<Task> getNextIfExist() {
//            String key = keys.pollFirst();
//            return key != null ? Optional.of(new TestTask(key)) : Optional.empty();
//        }
//
//        @Override
//        public String getId() {
//            return TEST_FACTORY;
//        }
//    }
//
//    @RequiredArgsConstructor
//    @Getter
//    private static class TestTask implements Task{
//        private final String key;
//
//        private boolean continuationPossible;
//
//        @Override
//        public void execute(Context context) {
//            continuationPossible = false;
//            if (!key.equals(TERMINATE_KEY)){
//                continuationPossible = true;
//                context.put(key, RESULT_PROPERTY, true);
//            }
//        }
//    }
//}
