package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.street.Street;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class BeforeStreetSavingChecker extends AbstractBeforeSavingChecker<Street> {
    @Override
    public Result<List<Street>> apply(Street value) {
        String name = value.getName();
        if (name == null || name.isEmpty()){
            return ImmutableResult.<List<Street>>fail("checking.domain.street.name.isEmpty");
        }
        return value.getCity() == null
                ? ImmutableResult.<List<Street>>fail("checking.domain.street.city.isEmpty")
                : ImmutableResult.<List<Street>>ok(List.of(value));
    }
}
