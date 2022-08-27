package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domain.Tag;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import kpn.lib.service.Service;

import java.util.List;

public final class TagServiceWrapper extends BaseServiceWrapper<Tag> {
    public static Service<Long, Tag, Predicate, Result<List<Tag>>> SERVICE;

    // TODO: 27.08.2022 ???
    @Override
    protected Service<Long, Tag, Predicate, Result<List<Tag>>> getService() {
        return null;
//        return SERVICE;
    }

    @Override
    protected Result<List<Tag>> createFailResult() {
        return ImmutableResult.<List<Tag>>fail("service.tag.null");
    }
}
