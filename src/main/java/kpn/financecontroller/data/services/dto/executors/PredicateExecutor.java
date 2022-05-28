package kpn.financecontroller.data.services.dto.executors;

import kpn.lib.result.Result;

import java.util.List;

public interface PredicateExecutor<DOMAIN, PREDICATE> {
    Result<List<DOMAIN>> execute(PREDICATE predicate);
}
