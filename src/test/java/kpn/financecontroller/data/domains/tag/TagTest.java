package kpn.financecontroller.data.domains.tag;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.ArrayDeque;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TagTest {

    @Test
    void shouldCheckGetInfo() {
        String expectedInfo = "some.name";
        Tag tag = new Tag();
        tag.setName(expectedInfo);

        assertThat(expectedInfo).isEqualTo(tag.getInfo());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckGetting.csv")
    void shouldCheckGetting(Long id, String name, String rawQueue, String expectedResult) {
        ArrayDeque<String> path = rawQueue != null
                ? new ArrayDeque<>(List.of(rawQueue.split("\\.")))
                : new ArrayDeque<>();

        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);

        String result = tag.getInDeep(path);
        Assertions.assertThat(expectedResult).isEqualTo(result);
    }
}