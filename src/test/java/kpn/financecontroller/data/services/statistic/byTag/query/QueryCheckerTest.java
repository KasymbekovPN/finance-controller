package kpn.financecontroller.data.services.statistic.byTag.query;

import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class QueryCheckerTest {

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
        Query query = new Query();
        query.setBeginTimeEnable(true);

        Result<Void> result = checker.apply(query);
        assertThat(expectedResultWhenWrongBeginTimeCondition).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenEndTimeConditionIsWrong() {
        Query query = new Query();
        query.setBeginTimeEnable(true);
        query.setBeginTime(LocalDate.now());
        query.setEndTimeEnable(true);

        Result<Void> result = checker.apply(query);
        assertThat(expectedResultWhenWrongEndTimeCondition).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenTagsConditionIsWrong() {
        Query query = new Query();
        query.setBeginTimeEnable(true);
        query.setBeginTime(LocalDate.now());
        query.setEndTimeEnable(true);
        query.setEndTime(LocalDate.now());

        Result<Void> result = checker.apply(query);
        assertThat(expectedResultWhenWrongTagsCondition).isEqualTo(result);
    }

    @Test
    void shouldCheck() {
        Query query = new Query();
        query.setBeginTimeEnable(true);
        query.setBeginTime(LocalDate.now());
        query.setEndTimeEnable(true);
        query.setEndTime(LocalDate.now());
        query.setForAllTags(true);

        Result<Void> result = checker.apply(query);
        assertThat(expectedResult).isEqualTo(result);
    }
}