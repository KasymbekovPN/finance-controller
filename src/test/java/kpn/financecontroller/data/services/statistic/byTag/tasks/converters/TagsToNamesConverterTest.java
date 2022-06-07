package kpn.financecontroller.data.services.statistic.byTag.tasks.converters;

import kpn.financecontroller.data.domains.tag.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class TagsToNamesConverterTest {

    @Test
    void shouldCheckConversion() {
        Set<Long> tagIds = Set.of(1L, 2L, 3L);
        Set<Tag> tags = tagIds.stream()
                .map(id -> {
                    Tag tag = new Tag();
                    tag.setId(id);
                    tag.setName("tag"+id);
                    return tag;
                })
                .collect(Collectors.toSet());

        String tagNamesAsString = new TagsToNamesConverter().apply(tags);
        assertThat(tagNamesAsString).isNotNull();

        Set<String> names = Arrays.stream(tagNamesAsString.split(", ")).collect(Collectors.toSet());
        Set<String> expectedNames = tags.stream().map(Tag::getName).collect(Collectors.toSet());
        assertThat(expectedNames).isEqualTo(names);
    }
}