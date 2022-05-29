package kpn.financecontroller.data.services.statistic.byTag.tasks.task;

import kpn.financecontroller.data.domains.tag.Tag;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

class ProductTaskTest {

    @Test
    void shouldCheckCopying() {
        Set<Tag> tags = createTags(1L, 2L, 3L);
        ProductTask task = createTask(tags);
        ProductTask copied = ProductTask.copy(task);

        Assertions.assertThat(task).isEqualTo(copied);
    }

    private ProductTask createTask(Set<Tag> tags) {
        ProductTask task = new ProductTask();
        task.setAllTags(false);
        task.setTags(tags);
        return task;
    }

    private Set<Tag> createTags(Long... ids) {
        return Arrays.stream(ids).map(id -> {
            Tag tag = new Tag();
            tag.setId(id);
            tag.setName("tag_" + id);
            return tag;
        }).collect(Collectors.toSet());
    }
}