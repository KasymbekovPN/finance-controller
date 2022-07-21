package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BeforeTagSavingCheckerTest {

    @Test
    void shouldCheck_whenNameNull() {
        Tag tag = new Tag();
        ImmutableResult<List<Tag>> expectedResult = ImmutableResult.<List<Tag>>fail("checking.domain.tag.name.isEmpty");

        Result<List<Tag>> result = new BeforeTagSavingChecker().apply(tag);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenNameEmpty() {
        Tag tag = new Tag();
        tag.setName("");
        ImmutableResult<List<Tag>> expectedResult = ImmutableResult.<List<Tag>>fail("checking.domain.tag.name.isEmpty");

        Result<List<Tag>> result = new BeforeTagSavingChecker().apply(tag);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck() {
        Tag tag = new Tag();
        tag.setName("some.name");
        ImmutableResult<List<Tag>> expectedResult = ImmutableResult.<List<Tag>>ok(List.of(tag));

        Result<List<Tag>> result = new BeforeTagSavingChecker().apply(tag);
        assertThat(expectedResult).isEqualTo(result);
    }
}