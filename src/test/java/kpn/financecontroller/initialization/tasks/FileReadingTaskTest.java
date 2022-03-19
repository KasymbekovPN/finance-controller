package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.initialization.generators.valued.*;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.lib.contexts.SimpleContext;
import kpn.taskexecutor.lib.tasks.Task;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FileReadingTaskTest {

    private static final String NOT_EXIST_PATH = "kpn/financecontroller/initialization/task/notExist.json";
    private static final String EXIST_PATH = "kpn/financecontroller/initialization/task/testFile.json";
    private static final ValuedGenerator<String> VALUED_GENERATOR = new ValuedStringGenerator();

    private static Result<String> expectedResultIfFileNotExist;

    // TODO: 17.03.2022 del
//    private static final String FILE_CONTENT = "{}";


    @BeforeAll
    static void beforeAll() {
        expectedResultIfFileNotExist = Result.<String>builder()
                .success(false)
                .code(VALUED_GENERATOR.generate(Entities.TAGS, Codes.FILE_NOT_EXIST))
                .build();
    }

    @Test
    void shouldCheckExecution_whenFileNotExist() {
        SimpleContext context = new SimpleContext();
//        FileReadingTask task = new FileReadingTask(NOT_EXIST_PATH);
//        task.execute(context);

//        Object o = context.get(VALUED_GENERATOR.generate(Entities.TAGS, Properties.FILE_CONTENT));

//        task.execute(context);
//        Optional<Object> maybeResult = context.get(KEY, FileReadingTask.Properties.RESULT.getValue());
//        assertThat(maybeResult).isPresent();
//        Result<String> result = (Result<String>) maybeResult.get();
//        assertThat(expectedResultWhenNoFile).isEqualTo(result);
//        assertThat(task.isContinuationPossible()).isFalse();
    }

//    @Test
//    void shouldCheckExecution() {
//        FileReadingTask task = new FileReadingTask(KEY);
//
//        ContextImpl context = new ContextImpl();
//        context.put(KEY, FileReadingTask.Properties.PATH.getValue(), EXIST_PATH);
//
//        task.execute(context);
//        Optional<Object> maybeResult = context.get(KEY, FileReadingTask.Properties.RESULT.getValue());
//        assertThat(maybeResult).isPresent();
//        Result<String> result = (Result<String>) maybeResult.get();
//        assertThat(expectedResult).isEqualTo(result);
//        assertThat(task.isContinuationPossible()).isTrue();
//    }
}
