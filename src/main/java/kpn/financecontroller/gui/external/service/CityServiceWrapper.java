package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domain.City;
import kpn.financecontroller.data.services.dto.service.CityDtoDecorator;
import kpn.lib.result.Result;
import kpn.lib.service.Service;

import java.util.List;

public final class CityServiceWrapper extends BaseServiceWrapper<City> {
    private static final Class<? extends Service<Long, City, Predicate, Result<List<City>>>> KEY = CityDtoDecorator.class;

    @Override
    protected Class<? extends Service<Long, City, Predicate, Result<List<City>>>> getKey() {
        return KEY;
    }
}
