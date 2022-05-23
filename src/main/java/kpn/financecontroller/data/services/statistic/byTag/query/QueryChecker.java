package kpn.financecontroller.data.services.statistic.byTag.query;

import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
final public class QueryChecker implements Function<Query, Result<Void>> {
    @Override
    public Result<Void> apply(Query query) {
        Result<Void> result = checkBeginTime(query);
        if (!result.isSuccess()) return result;

        result = checkEndTime(query);
        if (!result.isSuccess()) return result;

        return checkTags(query);
    }

    private Result<Void> checkBeginTime(Query query) {
        return query.isBeginTimeEnable() && query.getBeginTime() == null
                ? createFailResult("checking.query.beginTime.invalid")
                : createOkResult();
    }

    private Result<Void> checkEndTime(Query query) {
        return query.isEndTimeEnable() && query.getEndTime() == null
                ? createFailResult("checking.query.endTime.invalid")
                : createOkResult();
    }

    private Result<Void> checkTags(Query query) {
        return !query.isForAllTags() && query.getTags().isEmpty()
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
