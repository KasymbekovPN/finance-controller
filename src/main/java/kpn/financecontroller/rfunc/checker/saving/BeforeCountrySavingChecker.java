package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.country.Country;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class BeforeCountrySavingChecker extends AbstractBeforeSavingChecker<Country> {
    @Override
    public Result<List<Country>> apply(Country value) {
        String name = value.getName();
        return name == null || name.isEmpty()
                ? ImmutableResult.<List<Country>>fail("checking.domain.country.name.isEmpty")
                : ImmutableResult.<List<Country>>ok(List.of(value));
    }
}
