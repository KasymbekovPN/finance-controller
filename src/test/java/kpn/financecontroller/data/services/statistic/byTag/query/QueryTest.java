package kpn.financecontroller.data.services.statistic.byTag.query;

import kpn.financecontroller.data.domains.tag.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class QueryTest {

    @Test
    void shouldCheckTassAsIdsGetting_whenNotSet() {
        Query query = new Query();
        assertThat(query.getTagsAsIds()).isEmpty();
    }

    @Test
    void shouldCheckTassAsIdsGetting() {
        List<Long> expectedIds = List.of(0L, 1L, 2L);
        List<Tag> tags = expectedIds.stream().map(this::createTag).collect(Collectors.toList());

        Query query = new Query();
        query.setTags(tags);

        assertThat(expectedIds).isEqualTo(query.getTagsAsIds());
    }

    private Tag createTag(Long id){
        Tag tag = new Tag();
        tag.setId(id);
        tag.setName("tag " + id);
        return tag;
    }
}