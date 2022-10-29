package kpn.financecontroller.data.services.action;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.textfield.TextArea;
import kpn.financecontroller.data.domain.Action;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ActionTaskTest {

    @Test
    void shouldCheckCalling_ifHeaderNull() throws Exception {
        Result<Component> result = new ActionTask(null, null).call();

        ImmutableResult<Component> expectedResult
                = ImmutableResult.<Component>fail("action-task-execution.header.null");
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckCalling_ifActionNull() throws Exception {
        Result<Component> result = new ActionTask("", null).call();

        ImmutableResult<Component> expectedResult = ImmutableResult.<Component>fail("action-task-execution.action.null");
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckCalling_ifActionAlgorithmNull() throws Exception {
        Result<Component> result = new ActionTask("", new Action()).call();

        ImmutableResult<Component> expectedResult = ImmutableResult.<Component>fail("action-task-execution.algorithm.null");
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckCalling_whenAlgorithmExecutionFail() throws Exception {
        Action action = new Action();
        action.setAlgorithm("123 / 0;");
        Result<Component> result = new ActionTask("", action).call();

        ImmutableResult<Component> expectedResult = ImmutableResult.<Component>bFail("action-task-execution.algorithm.exception")
                .arg("ArithmeticException")
                .arg("Division by zero")
                .build();
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckCalling_ifAlgorithmResultNull() throws Exception {
        String algorithm = """
                Object o = null;
                o;
                """;
        Action action = new Action();
        action.setAlgorithm(algorithm);

        Result<Component> result = new ActionTask("", action).call();
        ImmutableResult<Component> expectedResult = ImmutableResult.<Component>fail("action-task-execution.algorithm.returns-null");
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckCalling_ifAlgorithmResultNotComponent() throws Exception {
        String algorithm = """
                Object o = 123;
                o;
                """;
        Action action = new Action();
        action.setAlgorithm(algorithm);

        Result<Component> result = new ActionTask("", action).call();
        ImmutableResult<Component> expectedResult = ImmutableResult.<Component>fail("action-task-execution.algorithm.returns-not-component");
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheckCalling() throws Exception {
        String header = """
                import com.vaadin.flow.component.Component;
                import com.vaadin.flow.component.textfield.TextArea;
                """;
        String algorithm = """
                TextArea textArea = new TextArea();
                textArea.setValue("some text");
                textArea;
                """;
        Action action = new Action();
        action.setAlgorithm(algorithm);

        Result<Component> result = new ActionTask(header, action).call();
        assertThat(result.getValue().getClass()).isEqualTo(TextArea.class);
        TextArea textArea = (TextArea) result.getValue();
        assertThat(textArea.getValue()).isEqualTo("some text");
    }
}