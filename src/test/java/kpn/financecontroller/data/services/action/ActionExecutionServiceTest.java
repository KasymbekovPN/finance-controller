package kpn.financecontroller.data.services.action;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextArea;
import kpn.financecontroller.data.domain.Action;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class ActionExecutionServiceTest {

    @Test
    void shouldCheckCalculate() throws ExecutionException, InterruptedException {
        ActionExecutionService service = new ActionExecutionService(new TestFactory());

        Future<Result<Component>> future = service.calculate(new Action());
        Result<Component> result = future.get();

        assertThat(result.getValue().getClass()).isEqualTo(TextArea.class);
    }

    @Test
    void shouldCheckCalculate_multiThread() throws ExecutionException, InterruptedException {
        ActionExecutionService service = new ActionExecutionService(new TestFactory());

        HashSet<Future<Result<Component>>> futures = new HashSet<>();
        Set<String> expectedDescriptions = Set.of("0", "1", "2", "3", "4");
        for (String description : expectedDescriptions) {
            Action action = new Action();
            action.setDescription(description);
            futures.add(service.calculate(action));
        }

        HashSet<String> descriptions = new HashSet<>();
        for (Future<Result<Component>> future : futures) {
            String description = ((TextArea) future.get().getValue()).getValue();
            descriptions.add(description);
        }

        assertThat(descriptions).isEqualTo(expectedDescriptions);
    }

    private static class TestFactory implements Function<Action, Callable<Result<Component>>> {

        @Override
        public Callable<Result<Component>> apply(Action action) {
            return new TestActionTask(action);
        }
    }

    private static class TestActionTask implements Callable<Result<Component>> {
        private final String text;
        public TestActionTask(Action action) {
            text = action != null && action.getDescription() != null
                    ? action.getDescription()
                    : "";
        }

        @Override
        public Result<Component> call() throws Exception {
            TextArea value = new TextArea();
            value.setValue(text);
            return ImmutableResult.<Component>ok(value);
        }
    }
}