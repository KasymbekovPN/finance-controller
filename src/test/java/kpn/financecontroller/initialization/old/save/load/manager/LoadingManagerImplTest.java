package kpn.financecontroller.initialization.old.save.load.manager;

import kpn.financecontroller.initialization.old.load.manager.LoadingManagerImpl;
import kpn.financecontroller.initialization.old.load.reader.ResourceFileReader;
import kpn.financecontroller.initialization.old.load.tasks.LoadingTask;
import kpn.financecontroller.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class LoadingManagerImplTest {

    private static final String RIGHT_PATH = "rightPath";
    private static final String WRONG_PATH = "wrongPath";
    private static final String FAIL_PATH_CALC_CODE = "path.calc.fail";
    private static final String FAIL_READ_CODE = "reading.fail";
    private static final String FILE_CONTENT = "file.content";
    private static final String WRONG_CONTENT_PATH = "wrong.content.path";
    private static final String WRONG_FILE_CONTENT = "wrong.file.content";
    private static final String WRONG_CONTENT_CODE = "wrong.content.code";

    private static LoadingManagerImpl manager;
    private static Result<Void> expectedResultWhenPathCalcFail;
    private static Result<Void> expectedResultWhenReadingFail;
    private static Result<Void> expectedResultWhenFillingFail;
    private static Result<Void> expectedResult;

    @BeforeAll
    static void beforeAll() {
        manager = new LoadingManagerImpl(createResourceFileReader());
        expectedResultWhenPathCalcFail = Result.<Void>builder().success(false).code(FAIL_PATH_CALC_CODE).build();
        expectedResultWhenReadingFail = Result.<Void>builder().success(false).code(FAIL_READ_CODE).build();
        expectedResultWhenFillingFail = Result.<Void>builder().success(false).code(WRONG_CONTENT_CODE).build();
        expectedResult = Result.<Void>builder().success(true).build();
    }

    @Test
    void shouldCheckExecutionWhenPathCalculationIsFail() {
        LoadingTask<Long, String> loadingTask = createTaskWithFailPathCalc();
        Result<Void> result = manager.execute(loadingTask);
        assertThat(expectedResultWhenPathCalcFail).isEqualTo(result);
    }

    private LoadingTask<Long, String> createTaskWithFailPathCalc() {
        TestLoadingTask task = Mockito.mock(TestLoadingTask.class);
        Mockito
                .when(task.calculatePath())
                .thenReturn(Result.<String>builder().success(false).code(FAIL_PATH_CALC_CODE).build());
        return task;
    }

    @Test
    void shouldCheckExecutionWhenReadingIsFail() {
        LoadingTask<Long, String> loadingTask = createTaskWithFailReading();
        Result<Void> result = manager.execute(loadingTask);
        assertThat(expectedResultWhenReadingFail).isEqualTo(result);
    }

    private LoadingTask<Long, String> createTaskWithFailReading() {
        TestLoadingTask task = Mockito.mock(TestLoadingTask.class);
        Mockito
                .when(task.calculatePath())
                .thenReturn(Result.<String>builder().success(true).value(WRONG_PATH).build());
        return task;
    }

    @Test
    void shouldCheckExecutionWhenCollectorFillingIsFail() {
        LoadingTask<Long, String> loadingTask = createTaskWithCollectorFilling();
        Result<Void> result = manager.execute(loadingTask);
        assertThat(expectedResultWhenFillingFail).isEqualTo(result);
    }

    private LoadingTask<Long, String> createTaskWithCollectorFilling() {
        TestLoadingTask task = Mockito.mock(TestLoadingTask.class);
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
        LoadingTask<Long, String> loadingTask = createTask();
        Result<Void> result = manager.execute(loadingTask);
        assertThat(expectedResult).isEqualTo(result);
    }

    private LoadingTask<Long, String> createTask() {
        TestLoadingTask task = Mockito.mock(TestLoadingTask.class);
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

    abstract private static class TestLoadingTask implements LoadingTask<Long, String> {
    }
}