package kpn.financecontroller.data.domains.product;

import kpn.financecontroller.data.domains.tag.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    @Test
    void shouldCheckInfoGetting() {
        Set<String> rawTags = Set.of("tag0", "tag1", "tag2");
        HashSet<Tag> tags = new HashSet<>();
        for (String rawTag : rawTags) {
            Tag tag = new Tag();
            tag.setName(rawTag);
            tags.add(tag);
        }

        Product product = new Product();
        product.setTags(tags);

        Set<String> split = new HashSet<>(Set.of(product.getInfo().split(", ")));
        for (String rawTag : rawTags) {
            split.remove(rawTag);
        }
        assertThat(split.size()).isZero();
    }
}