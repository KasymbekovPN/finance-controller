package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domain.Country;
import kpn.financecontroller.data.services.dto.service.CountryDtoDecorator;
import kpn.lib.result.Result;
import kpn.lib.service.Service;

import java.util.List;

public final class CountryServiceWrapper extends BaseServiceWrapper<Country> {
    private static final Class<? extends Service<Long, Country, Predicate, Result<List<Country>>>> KEY = CountryDtoDecorator.class;

    @Override
    protected Class<? extends Service<Long, Country, Predicate, Result<List<Country>>>> getKey() {
        return KEY;
    }
}
