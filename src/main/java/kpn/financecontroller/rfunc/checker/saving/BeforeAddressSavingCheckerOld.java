package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.address.Address;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

// TODO: 21.07.2022 remake
@Component
final public class BeforeAddressSavingCheckerOld extends AbstractBeforeSavingCheckerOld<Address> {

    @Override
    public Result<Address> apply(Address value) {
        String name = value.getName();
        if (name == null || name.isEmpty()){
            return ImmutableResult.<Address>fail("checking.domain.address.name.isEmpty");
        }
        return value.getStreet() == null
                ? ImmutableResult.<Address>fail("checking.domain.address.street.isEmpty")
                : ImmutableResult.<Address>ok(value);
    }
}
