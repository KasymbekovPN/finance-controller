package kpn.financecontroller.data.services.statistic.byTag.query;

import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
final public class QueryChecker implements Function<QueryOld, Result<Void>> {
    @Override
    public Result<Void> apply(QueryOld queryOld) {
        Result<Void> result = checkBeginTime(queryOld);
        if (!result.isSuccess()) return result;

        result = checkEndTime(queryOld);
        if (!result.isSuccess()) return result;

        return checkTags(queryOld);
    }

    private Result<Void> checkBeginTime(QueryOld queryOld) {
        return queryOld.isBeginTimeEnable() && queryOld.getBeginTime() == null
                ? createFailResult("checking.query.beginTime.invalid")
                : createOkResult();
    }

    private Result<Void> checkEndTime(QueryOld queryOld) {
        return queryOld.isEndTimeEnable() && queryOld.getEndTime() == null
                ? createFailResult("checking.query.endTime.invalid")
                : createOkResult();
    }

    private Result<Void> checkTags(QueryOld queryOld) {
        return !queryOld.isForAllTags() && queryOld.getTags().isEmpty()
                ? createFailResult("checking.query.tags.invalid")
                : createOkResult();
    }

    private Result<Void> createFailResult(String code) {
        return ImmutableResult.<Void>fail(code);
    }

    private Result<Void> createOkResult() {
        return ImmutableResult.<Void>ok(null);
    }
}
