package kpn.financecontroller.data.services.statistic.byTag.query;

import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class QueryOldCheckerTest {

    private static QueryChecker checker;
    private static ImmutableResult<Void> expectedResultWhenWrongBeginTimeCondition;
    private static ImmutableResult<Void> expectedResultWhenWrongEndTimeCondition;
    private static ImmutableResult<Void> expectedResultWhenWrongTagsCondition;
    private static ImmutableResult<Void> expectedResult;

    @BeforeAll
    static void beforeAll() {
        expectedResultWhenWrongBeginTimeCondition = ImmutableResult.<Void>fail("checking.query.beginTime.invalid");
        expectedResultWhenWrongEndTimeCondition = ImmutableResult.<Void>fail("checking.query.endTime.invalid");
        expectedResultWhenWrongTagsCondition = ImmutableResult.<Void>fail("checking.query.tags.invalid");
        expectedResult = ImmutableResult.<Void>ok(null);

        checker = new QueryChecker();
    }

    @Test
    void shouldCheck_whenBeginTimeConditionIsWrong() {
        QueryOld queryOld = new QueryOld();
        queryOld.setBeginTimeEnable(true);

        Result<Void> result = checker.apply(queryOld);
        assertThat(expectedResultWhenWrongBeginTimeCondition).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenEndTimeConditionIsWrong() {
        QueryOld queryOld = new QueryOld();
        queryOld.setBeginTimeEnable(true);
        queryOld.setBeginTime(LocalDate.now());
        queryOld.setEndTimeEnable(true);

        Result<Void> result = checker.apply(queryOld);
        assertThat(expectedResultWhenWrongEndTimeCondition).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenTagsConditionIsWrong() {
        QueryOld queryOld = new QueryOld();
        queryOld.setBeginTimeEnable(true);
        queryOld.setBeginTime(LocalDate.now());
        queryOld.setEndTimeEnable(true);
        queryOld.setEndTime(LocalDate.now());

        Result<Void> result = checker.apply(queryOld);
        assertThat(expectedResultWhenWrongTagsCondition).isEqualTo(result);
    }

    @Test
    void shouldCheck() {
        QueryOld queryOld = new QueryOld();
        queryOld.setBeginTimeEnable(true);
        queryOld.setBeginTime(LocalDate.now());
        queryOld.setEndTimeEnable(true);
        queryOld.setEndTime(LocalDate.now());
        queryOld.setForAllTags(true);

        Result<Void> result = checker.apply(queryOld);
        assertThat(expectedResult).isEqualTo(result);
    }
}