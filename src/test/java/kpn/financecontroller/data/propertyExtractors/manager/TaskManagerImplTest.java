package kpn.financecontroller.data.propertyExtractors.manager;

import kpn.financecontroller.data.propertyExtractors.ResourceFileReader;
import kpn.financecontroller.data.propertyExtractors.tasks.Task;
import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class TaskManagerImplTest {

    private static final String RIGHT_PATH = "rightPath";
    private static final String WRONG_PATH = "wrongPath";
    private static final String FAIL_PATH_CALC_CODE = "path.calc.fail";
    private static final String FAIL_READ_CODE = "reading.fail";
    private static final String FILE_CONTENT = "file.content";
    private static final String WRONG_CONTENT_PATH = "wrong.content.path";
    private static final String WRONG_FILE_CONTENT = "wrong.file.content";
    private static final String WRONG_CONTENT_CODE = "wrong.content.code";

    private static TaskManagerImpl manager;
    private static Result<Void> expectedResultWhenPathCalcFail;
    private static Result<Void> expectedResultWhenReadingFail;
    private static Result<Void> expectedResultWhenFillingFail;
    private static Result<Void> expectedResult;

    @BeforeAll
    static void beforeAll() {
        manager = new TaskManagerImpl(createResourceFileReader());
        expectedResultWhenPathCalcFail = Result.<Void>builder().success(false).code(FAIL_PATH_CALC_CODE).build();
        expectedResultWhenReadingFail = Result.<Void>builder().success(false).code(FAIL_READ_CODE).build();
        expectedResultWhenFillingFail = Result.<Void>builder().success(false).code(WRONG_CONTENT_CODE).build();
        expectedResult = Result.<Void>builder().success(true).build();
    }

    @Test
    void shouldCheckExecutionWhenPathCalculationIsFail() {
        Task<Long, String> task = createTaskWithFailPathCalc();
        Result<Void> result = manager.execute(task);
        assertThat(expectedResultWhenPathCalcFail).isEqualTo(result);
    }

    private Task<Long, String> createTaskWithFailPathCalc() {
        TestTask task = Mockito.mock(TestTask.class);
        Mockito
                .when(task.calculatePath())
                .thenReturn(Result.<String>builder().success(false).code(FAIL_PATH_CALC_CODE).build());
        return task;
    }

    @Test
    void shouldCheckExecutionWhenReadingIsFail() {
        Task<Long, String> task = createTaskWithFailReading();
        Result<Void> result = manager.execute(task);
        assertThat(expectedResultWhenReadingFail).isEqualTo(result);
    }

    private Task<Long, String> createTaskWithFailReading() {
        TestTask task = Mockito.mock(TestTask.class);
        Mockito
                .when(task.calculatePath())
                .thenReturn(Result.<String>builder().success(true).value(WRONG_PATH).build());
        return task;
    }

    @Test
    void shouldCheckExecutionWhenCollectorFillingIsFail() {
        Task<Long, String> task = createTaskWithCollectorFilling();
        Result<Void> result = manager.execute(task);
        assertThat(expectedResultWhenFillingFail).isEqualTo(result);
    }

    private Task<Long, String> createTaskWithCollectorFilling() {
        TestTask task = Mockito.mock(TestTask.class);
        Mockito
                .when(task.calculatePath())
                .thenReturn(Result.<String>builder().success(true).value(WRONG_CONTENT_PATH).build());
        Mockito
                .when(task.fillCollector(WRONG_FILE_CONTENT))
                .thenReturn(Result.<Void>builder().success(false).code(WRONG_CONTENT_CODE).build());
        return task;
    }

    @Test
    void shouldCheckExecution() {
        Task<Long, String> task = createTask();
        Result<Void> result = manager.execute(task);
        assertThat(expectedResult).isEqualTo(result);
    }

    private Task<Long, String> createTask() {
        TestTask task = Mockito.mock(TestTask.class);
        Mockito
                .when(task.calculatePath())
                .thenReturn(Result.<String>builder().success(true).value(RIGHT_PATH).build());
        Mockito
                .when(task.fillCollector(FILE_CONTENT))
                .thenReturn(Result.<Void>builder().success(true).build());
        return task;
    }

    private static ResourceFileReader createResourceFileReader(){
        ResourceFileReader reader = Mockito.mock(ResourceFileReader.class);

        Mockito
                .when(reader.read(WRONG_PATH))
                .thenReturn(Result.<String>builder().success(false).code(FAIL_READ_CODE).build());
        Mockito
                .when(reader.read(WRONG_CONTENT_PATH))
                .thenReturn(Result.<String>builder().success(true).value(WRONG_FILE_CONTENT).build());
        Mockito
                .when(reader.read(RIGHT_PATH))
                .thenReturn(Result.<String>builder().success(true).value(FILE_CONTENT).build());

        return reader;
    }

    abstract private static class TestTask implements Task<Long, String>{
    }
}