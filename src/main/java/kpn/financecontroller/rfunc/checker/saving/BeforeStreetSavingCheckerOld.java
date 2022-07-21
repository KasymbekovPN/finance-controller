package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.street.Street;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

// TODO: 21.07.2022 remake
@Component
final public class BeforeStreetSavingCheckerOld extends AbstractBeforeSavingCheckerOld<Street> {
    @Override
    public Result<Street> apply(Street value) {
        String name = value.getName();
        if (name == null || name.isEmpty()){
            return ImmutableResult.<Street>fail("checking.domain.street.name.isEmpty");
        }
        return value.getCity() == null
            ? ImmutableResult.<Street>fail("checking.domain.street.city.isEmpty")
            : ImmutableResult.<Street>ok(value);
    }
}
