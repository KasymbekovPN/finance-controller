package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.city.City;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

@Component
final public class BeforeCitySavingChecker extends AbstractBeforeSavingChecker<City> {
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
