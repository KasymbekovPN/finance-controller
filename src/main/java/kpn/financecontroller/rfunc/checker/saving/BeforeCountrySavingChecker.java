package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.country.Country;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

@Component
final public class BeforeCountrySavingChecker extends AbstractBeforeSavingChecker<Country> {
    @Override
    public Result<Country> apply(Country value) {
        String name = value.getName();
        return name == null || name.isEmpty()
                ? ImmutableResult.<Country>fail("checking.domain.country.name.isEmpty").build()
                : ImmutableResult.<Country>ok(value).build();
    }
}
