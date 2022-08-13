package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domain.Action;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BeforeActionSavingCheckerTest {

    @Test
    void shouldCheck_whenDescriptionNull() {
        ImmutableResult<List<Action>> expectedResult
                = ImmutableResult.<List<Action>>fail("checking.domain.action.description.null");
        Action action = new Builder().build();

        BeforeActionSavingChecker checker = new BeforeActionSavingChecker();
        Result<List<Action>> result = checker.apply(action);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheck_whenDescriptionEmpty() {
        ImmutableResult<List<Action>> expectedResult
                = ImmutableResult.<List<Action>>fail("checking.domain.action.description.empty");
        Action action = new Builder().description("").build();

        BeforeActionSavingChecker checker = new BeforeActionSavingChecker();
        Result<List<Action>> result = checker.apply(action);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheck_whenAlgorithmNull() {
        ImmutableResult<List<Action>> expectedResult
                = ImmutableResult.<List<Action>>fail("checking.domain.action.algorithm.null");
        Action action = new Builder().description("some description").build();

        BeforeActionSavingChecker checker = new BeforeActionSavingChecker();
        Result<List<Action>> result = checker.apply(action);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheck_whenAlgorithmEmpty() {
        ImmutableResult<List<Action>> expectedResult
                = ImmutableResult.<List<Action>>fail("checking.domain.action.algorithm.empty");
        Action action = new Builder().description("some description").algorithm("").build();

        BeforeActionSavingChecker checker = new BeforeActionSavingChecker();
        Result<List<Action>> result = checker.apply(action);

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void shouldCheck() {
        Action action = new Builder().description("some description").algorithm("some algorithm").build();
        ImmutableResult<List<Action>> expectedResult = ImmutableResult.<List<Action>>ok(List.of(action));

        BeforeActionSavingChecker checker = new BeforeActionSavingChecker();
        Result<List<Action>> result = checker.apply(action);

        assertThat(result).isEqualTo(expectedResult);
    }

    private static class Builder{
        private String description;
        private String algorithm;

        Builder description(String description){
            this.description = description;
            return this;
        }

        Builder algorithm(String algorithm){
            this.algorithm = algorithm;
            return this;
        }

        Action build(){
            Action action = new Action();
            action.setDescription(description);
            action.setAlgorithm(algorithm);
            return action;
        }
    }
}