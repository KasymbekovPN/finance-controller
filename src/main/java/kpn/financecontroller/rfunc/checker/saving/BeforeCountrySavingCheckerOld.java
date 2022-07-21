package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.country.Country;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

// TODO: 21.07.2022 remake
@Component
final public class BeforeCountrySavingCheckerOld extends AbstractBeforeSavingCheckerOld<Country> {
    @Override
    public Result<Country> apply(Country value) {
        String name = value.getName();
        return name == null || name.isEmpty()
                ? ImmutableResult.<Country>fail("checking.domain.country.name.isEmpty")
                : ImmutableResult.<Country>ok(value);
    }
}
