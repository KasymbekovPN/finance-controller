package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.address.Address;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

@Component
final public class BeforeAddressSavingChecker extends AbstractBeforeSavingChecker<Address> {

    @Override
    public Result<Address> apply(Address value) {
        String name = value.getName();
        if (name == null || name.isEmpty()){
            return ImmutableResult.<Address>fail("checking.domain.address.name.isEmpty").build();
        }
        return value.getStreet() == null
                ? ImmutableResult.<Address>fail("checking.domain.address.street.isEmpty").build()
                : ImmutableResult.<Address>ok(value).build();
    }
}
