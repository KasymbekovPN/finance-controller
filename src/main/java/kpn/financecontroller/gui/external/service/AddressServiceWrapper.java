package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domain.Address;
import kpn.financecontroller.data.services.dto.service.AddressDtoDecorator;
import kpn.financecontroller.annotation.External;
import kpn.lib.result.Result;
import kpn.lib.service.Service;

import java.util.List;

@External
public final class AddressServiceWrapper extends BaseServiceWrapper<Address> {
    private static final Class<? extends Service<Long, Address, Predicate, Result<List<Address>>>> KEY = AddressDtoDecorator.class;

    @Override
    protected Class<? extends Service<Long, Address, Predicate, Result<List<Address>>>> getKey() {
        return KEY;
    }
}
