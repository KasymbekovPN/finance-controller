package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domain.Seller;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class BeforeSellerSavingChecker extends AbstractBeforeSavingChecker<Seller> {
    @Override
    public Result<List<Seller>> apply(Seller value) {
        String name = value.getName();
        return name == null || name.isEmpty()
                ? ImmutableResult.<List<Seller>>fail("checking.domain.seller.name.isEmpty")
                : ImmutableResult.<List<Seller>>ok(List.of(value));
    }
}
