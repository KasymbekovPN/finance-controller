package kpn.financecontroller.data.services.statistic.byTag.query;

import kpn.financecontroller.data.domains.tag.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class QueryOldTest {

    @Test
    void shouldCheckTassAsIdsGetting_whenNotSet() {
        QueryOld queryOld = new QueryOld();
        assertThat(queryOld.getTagsAsIds()).isEmpty();
    }

    @Test
    void shouldCheckTassAsIdsGetting() {
        List<Long> expectedIds = List.of(0L, 1L, 2L);
        List<Tag> tags = expectedIds.stream().map(this::createTag).collect(Collectors.toList());

        QueryOld queryOld = new QueryOld();
        queryOld.setTags(tags);

        assertThat(expectedIds).isEqualTo(queryOld.getTagsAsIds());
    }

    private Tag createTag(Long id){
        Tag tag = new Tag();
        tag.setId(id);
        tag.setName("tag " + id);
        return tag;
    }
}