package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.street.Street;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

@Component
final public class BeforeStreetSavingChecker extends AbstractBeforeSavingChecker<Street> {
    @Override
    public Result<Street> apply(Street value) {
        String name = value.getName();
        if (name == null || name.isEmpty()){
            return ImmutableResult.<Street>fail("checking.domain.street.name.isEmpty").build();
        }
        return value.getCity() == null
            ? ImmutableResult.<Street>fail("checking.domain.street.city.isEmpty").build()
            : ImmutableResult.<Street>ok(value).build();
    }
}
