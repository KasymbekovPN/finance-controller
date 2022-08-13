package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domain.Action;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class BeforeActionSavingChecker extends AbstractBeforeSavingChecker<Action> {
    @Override
    public Result<List<Action>> apply(Action value) {
        String description = value.getDescription();
        if (description == null) {
            return ImmutableResult.<List<Action>>fail("checking.domain.action.description.null");
        }
        if (description.isEmpty()){
            return ImmutableResult.<List<Action>>fail("checking.domain.action.description.empty");
        }

        String algorithm = value.getAlgorithm();
        if (algorithm == null){
            return ImmutableResult.<List<Action>>fail("checking.domain.action.algorithm.null");
        }
        if (algorithm.isEmpty()){
            return ImmutableResult.<List<Action>>fail("checking.domain.action.algorithm.empty");
        }

        return ImmutableResult.<List<Action>>ok(List.of(value));
    }
}
