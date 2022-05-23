package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.seller.Seller;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

@Component
final public class BeforeSellerSavingChecker extends AbstractBeforeSavingChecker<Seller> {
    @Override
    public Result<Seller> apply(Seller value) {
        String name = value.getName();
        return name == null || name.isEmpty()
                ? ImmutableResult.<Seller>fail("checking.domain.seller.name.isEmpty")
                : ImmutableResult.<Seller>ok(value);
    }
}
