package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domain.Seller;
import kpn.financecontroller.data.services.dto.service.SellerDtoDecorator;
import kpn.lib.result.Result;
import kpn.lib.service.Service;

import java.util.List;

public final class SellerServiceWrapper extends BaseServiceWrapper<Seller> {
    private static final Class<? extends Service<Long, Seller, Predicate, Result<List<Seller>>>> KEY = SellerDtoDecorator.class;

    @Override
    protected Class<? extends Service<Long, Seller, Predicate, Result<List<Seller>>>> getKey() {
        return KEY;
    }
}
