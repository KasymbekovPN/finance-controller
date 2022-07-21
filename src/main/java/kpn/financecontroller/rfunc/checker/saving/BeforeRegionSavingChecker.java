package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.region.Region;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class BeforeRegionSavingChecker extends AbstractBeforeSavingChecker<Region> {
    @Override
    public Result<List<Region>> apply(Region value) {
        String name = value.getName();
        if (name == null || name.isEmpty()){
            return ImmutableResult.<List<Region>>fail("checking.domain.region.name.isEmpty");
        }
        return value.getCountry() == null
                ? ImmutableResult.<List<Region>>fail("checking.domain.region.country.isEmpty")
                : ImmutableResult.<List<Region>>ok(List.of(value));
    }
}
