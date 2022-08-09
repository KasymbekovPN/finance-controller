package kpn.financecontroller.data.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TagTest {

    @Test
    void shouldCheckGetInfo() {
        String expectedInfo = "some.name";
        Tag tag = new Tag();
        tag.setName(expectedInfo);

        assertThat(expectedInfo).isEqualTo(tag.getInfo());
    }
}