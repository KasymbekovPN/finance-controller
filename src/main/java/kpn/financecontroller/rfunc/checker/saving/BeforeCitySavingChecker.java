package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.city.City;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class BeforeCitySavingChecker extends AbstractBeforeSavingChecker<City> {
    @Override
    public Result<List<City>> apply(City value) {
        String name = value.getName();
        if (name == null || name.isEmpty()){
            return ImmutableResult.<List<City>>fail("checking.domain.city.name.isEmpty");
        }
        return value.getRegion() == null
                ? ImmutableResult.<List<City>>fail("checking.domain.city.region.isEmpty")
                : ImmutableResult.<List<City>>ok(List.of(value));
    }
}
