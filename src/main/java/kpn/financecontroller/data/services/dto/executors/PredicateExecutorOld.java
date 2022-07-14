package kpn.financecontroller.data.services.dto.executors;

import kpn.lib.result.Result;

import java.util.List;

// TODO: 14.07.2022 del
// TODO: 28.05.2022 it must be BaseExecutor
// TODO: 28.05.2022 create if. PredicateExecutor<DOMAIN>
public interface PredicateExecutorOld<DOMAIN, PREDICATE> {
    Result<List<DOMAIN>> execute(PREDICATE predicate);
}
