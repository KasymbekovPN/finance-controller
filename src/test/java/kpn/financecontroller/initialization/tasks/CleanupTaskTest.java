package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.data.services.deleters.Deleter;
import kpn.financecontroller.initialization.generators.valued.*;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.tasks.testUtils.TestKeys;
import kpn.financecontroller.initialization.tasks.testUtils.TestManagerCreator;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.contexts.DefaultContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class CleanupTaskTest {

    private static final Valued<String> KEY = TestKeys.KEY;
    private static final ValuedGenerator<String> VALUED_GENERATOR = new ValuedStringGenerator();
    private static final Function<Context, ResultContextManager> CREATOR = new TestManagerCreator();

    private static Result<Void> expectedResult_ifCleaningFail;
    private static Result<Void> expectedResult;

    @BeforeAll
    static void beforeAll() {
        expectedResult_ifCleaningFail = ImmutableResult.<Void>fail(VALUED_GENERATOR.generate(KEY, Codes.DB_FAIL_CLEANING))
                .arg(KEY)
                .build();
        expectedResult = ImmutableResult.<Void>builder()
                .success(true)
                .arg(KEY)
                .build();
    }

    @Test
    void shouldCheckExecution_ifCleaningFail() {
        DefaultContext context = new DefaultContext();
        CleanupTask task = new CleanupTask();
        task.setKey(KEY);
        task.setValuedGenerator(VALUED_GENERATOR);
        task.setManagerCreator(CREATOR);
        task.setDtoService(createDtoService(createFailDeleter()));

        task.execute(context);

        Result<Void> result = CREATOR.apply(context).get(KEY, Properties.DB_CLEANING_RESULT, Void.class);
        assertThat(expectedResult_ifCleaningFail).isEqualTo(result);
    }

    @Test
    void shouldCheckExecution() {
        DefaultContext context = new DefaultContext();
        CleanupTask task = new CleanupTask();
        task.setKey(KEY);
        task.setValuedGenerator(VALUED_GENERATOR);
        task.setManagerCreator(CREATOR);
        task.setDtoService(createDtoService(createDeleter()));

        task.execute(context);

        Result<Void> result = CREATOR.apply(context).get(KEY, Properties.DB_CLEANING_RESULT, Void.class);
        assertThat(expectedResult).isEqualTo(result);
    }

    private TestDeleter createDeleter() {
        TestDeleter deleter = Mockito.mock(TestDeleter.class);
        Mockito
                .when(deleter.all())
                .thenReturn(ImmutableResult.<Void>builder().success(true).build());
        return deleter;
    }

    private TestDeleter createFailDeleter() {
        TestDeleter deleter = Mockito.mock(TestDeleter.class);
        Mockito
                .when(deleter.all())
                .thenReturn(ImmutableResult.<Void>builder().success(false).build());
        return deleter;
    }

    private TestDtoService createDtoService(TestDeleter deleter) {
        TestDtoService dtoService = Mockito.mock(TestDtoService.class);
        Mockito
                .when(dtoService.deleter())
                .thenReturn(deleter);
        return dtoService;
    }

    public abstract static class TestDeleter implements Deleter<Tag, TagEntity, Long>{}
    public abstract static class TestDtoService implements DTOService<Tag, TagEntity, Long>{}
}
