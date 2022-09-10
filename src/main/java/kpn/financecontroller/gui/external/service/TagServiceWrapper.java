package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domain.Tag;
import kpn.financecontroller.data.services.dto.service.TagDtoDecorator;
import kpn.financecontroller.annotation.External;
import kpn.lib.result.Result;
import kpn.lib.service.Service;

import java.util.List;

@External
public final class TagServiceWrapper extends BaseServiceWrapper<Tag> {
    private static final Class<? extends Service<Long, Tag, Predicate, Result<List<Tag>>>> KEY = TagDtoDecorator.class;

    @Override
    protected Class<? extends Service<Long, Tag, Predicate, Result<List<Tag>>>> getKey() {
        return KEY;
    }
}
