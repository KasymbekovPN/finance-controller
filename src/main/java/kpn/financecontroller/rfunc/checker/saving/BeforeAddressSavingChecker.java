package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domain.Address;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class BeforeAddressSavingChecker extends AbstractBeforeSavingChecker<Address> {

    @Override
    public Result<List<Address>> apply(Address value) {
        String name = value.getName();
        if (name == null || name.isEmpty()){
            return ImmutableResult.<List<Address>>fail("checking.domain.address.name.isEmpty");
        }
        return value.getStreet() == null
                ? ImmutableResult.<List<Address>>fail("checking.domain.address.street.isEmpty")
                : ImmutableResult.<List<Address>>ok(List.of(value));
    }
}
