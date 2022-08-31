package kpn.financecontroller.gui.external.service;

import com.querydsl.core.types.Predicate;
import kpn.lib.domain.Domain;
import kpn.lib.result.Result;

import java.util.List;

public interface ServiceWrapper<D extends Domain<Long>>{
    Result<List<D>> find(Predicate predicate);
}
