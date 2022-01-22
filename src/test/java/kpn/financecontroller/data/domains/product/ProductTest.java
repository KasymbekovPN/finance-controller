package kpn.financecontroller.data.domains.product;

import kpn.financecontroller.data.domains.tag.Tag;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

class ProductTest {

    @Test
    void shouldCheckGettingTagsAsString() {
        int tagAmount = 3;
        HashSet<Tag> tags = new HashSet<>();
        for (int i = 0; i < tagAmount; i++) {
            tags.add(new Tag((long) i, String.format("tag%s", i)));
        }

        Product product = new Product();
        product.setTags(tags);

        String expected = createTagsAsString(tags);
        Assertions.assertThat(expected).isEqualTo(product.getTagsAsStr());
    }

    private String createTagsAsString(HashSet<Tag> tags) {
        StringBuilder result = new StringBuilder();
        String delimiter = "";

        for (Tag tag : tags) {
            result.append(delimiter).append(tag.toString());
            delimiter = ", ";
        }

        return result.toString();
    }
}