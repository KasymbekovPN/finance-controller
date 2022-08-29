package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domain.Street;
import kpn.financecontroller.data.services.dto.service.StreetDtoDecorator;
import kpn.lib.result.Result;
import kpn.lib.service.Service;

import java.util.List;

public final class StreetServiceWrapper extends BaseServiceWrapper<Street>{
    private static final Class<? extends Service<Long, Street, Predicate, Result<List<Street>>>> KEY = StreetDtoDecorator.class;

    @Override
    protected Class<? extends Service<Long, Street, Predicate, Result<List<Street>>>> getKey() {
        return KEY;
    }
}
