package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.region.Region;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

@Component
final public class BeforeRegionSavingChecker extends AbstractBeforeSavingChecker<Region> {
    @Override
    public Result<Region> apply(Region value) {
        String name = value.getName();
        if (name == null || name.isEmpty()){
            return ImmutableResult.<Region>fail("checking.domain.region.name.isEmpty").build();
        }
        return value.getCountry() == null
            ? ImmutableResult.<Region>fail("checking.domain.region.country.isEmpty").build()
            : ImmutableResult.<Region>ok(value).build();
    }
}
