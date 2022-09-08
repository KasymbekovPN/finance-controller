package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domain.Region;
import kpn.financecontroller.data.services.dto.service.RegionDtoDecorator;
import kpn.financecontroller.annotation.External;
import kpn.lib.result.Result;
import kpn.lib.service.Service;

import java.util.List;

@External
public final class RegionServiceWrapper extends BaseServiceWrapper<Region> {
    private static final Class<? extends Service<Long, Region, Predicate, Result<List<Region>>>> KEY = RegionDtoDecorator.class;

    @Override
    protected Class<? extends Service<Long, Region, Predicate, Result<List<Region>>>> getKey() {
        return KEY;
    }
}
