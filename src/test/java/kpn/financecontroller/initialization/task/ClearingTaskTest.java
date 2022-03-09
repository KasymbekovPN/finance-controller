package kpn.financecontroller.initialization.task;

import kpn.financecontroller.data.domains.AbstractDomain;
import kpn.financecontroller.data.entities.AbstractEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.data.services.deleters.Deleter;
import kpn.financecontroller.initialization.context.ContextImpl;
import kpn.financecontroller.result.Result;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ClearingTaskTest {

    private static final String KEY = "key";

    private static Result<Void> expectedResultWhenFail;
    private static Result<Void> expectedResult;

    @BeforeAll
    static void beforeAll() {
        expectedResultWhenFail = Result.<Void>builder()
                .success(false)
                .arg(KEY)
                .build();
        expectedResult = Result.<Void>builder()
                .success(true)
                .arg(KEY)
                .build();
    }

    @Test
    void shouldCheckExecution_whenDeletingFail() {
        ClearingTask task = new ClearingTask(KEY, createDTOService(createDeleter(false)));

        ContextImpl context = new ContextImpl();
        task.execute(context);

        Optional<Object> maybeResult = context.get(KEY, ClearingTask.Properties.RESULT.getValue());
        assertThat(maybeResult).isPresent();

        Result<Void> result = (Result<Void>) maybeResult.get();
        assertThat(expectedResultWhenFail).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isFalse();
    }

    @Test
    void shouldCheckExecution() {
        ClearingTask task = new ClearingTask(KEY, createDTOService(createDeleter(true)));

        ContextImpl context = new ContextImpl();
        task.execute(context);

        Optional<Object> maybeResult = context.get(KEY, ClearingTask.Properties.RESULT.getValue());
        assertThat(maybeResult).isPresent();

        Result<Void> result = (Result<Void>) maybeResult.get();
        assertThat(expectedResult).isEqualTo(result);
        assertThat(task.isContinuationPossible()).isTrue();
    }

    private Deleter<TestDomain, TestEntity, Long> createDeleter(boolean result){
        TestDeleter deleter = Mockito.mock(TestDeleter.class);
        Mockito
                .when(deleter.all())
                .thenReturn(Result.<Void>builder().success(result).arg(KEY).build());
        return deleter;
    }

    private DTOService<TestDomain, TestEntity, Long> createDTOService(Deleter<TestDomain, TestEntity, Long> deleter){
        TestDTOService service = Mockito.mock(TestDTOService.class);
        Mockito
                .when(service.deleter())
                .thenReturn(deleter);
        return service;
    }

    private abstract static class TestDTOService implements DTOService<TestDomain, TestEntity, Long>{}
    private abstract static class TestDeleter implements Deleter<TestDomain, TestEntity, Long>{}

    @Getter
    @Setter
    @NoArgsConstructor
    private static class TestDomain extends AbstractDomain{}

    @Getter
    @Setter
    @NoArgsConstructor
    private static class TestEntity extends AbstractEntity{}
}
