package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.city.City;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

// TODO: 21.07.2022 remake
@Component
final public class BeforeCitySavingCheckerOld extends AbstractBeforeSavingCheckerOld<City> {
    @Override
    public Result<City> apply(City value) {
        String name = value.getName();
        if (name == null || name.isEmpty()){
            return ImmutableResult.<City>fail("checking.domain.city.name.isEmpty");
        }
        return value.getRegion() == null
                ? ImmutableResult.<City>fail("checking.domain.city.region.isEmpty")
                : ImmutableResult.<City>ok(value);
    }
}
