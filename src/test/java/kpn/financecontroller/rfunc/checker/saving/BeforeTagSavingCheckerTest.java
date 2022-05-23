package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.tag.Tag;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BeforeTagSavingCheckerTest {

    @Test
    void shouldCheck_whenNameNull() {
        Tag tag = new Tag();
        ImmutableResult<Tag> expectedResult = ImmutableResult.<Tag>fail("checking.domain.tag.name.isEmpty");

        Result<Tag> result = new BeforeTagSavingChecker().apply(tag);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenNameEmpty() {
        Tag tag = new Tag();
        tag.setName("");
        ImmutableResult<Tag> expectedResult = ImmutableResult.<Tag>fail("checking.domain.tag.name.isEmpty");

        Result<Tag> result = new BeforeTagSavingChecker().apply(tag);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck() {
        Tag tag = new Tag();
        tag.setName("some.name");
        ImmutableResult<Tag> expectedResult = ImmutableResult.<Tag>ok(tag);

        Result<Tag> result = new BeforeTagSavingChecker().apply(tag);
        assertThat(expectedResult).isEqualTo(result);
    }
}