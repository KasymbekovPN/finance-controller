package kpn.financecontroller.data.domains.product;

import kpn.financecontroller.data.domains.tag.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.*;
import java.util.stream.Collectors;

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

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckGetting.csv", delimiter = '|')
    void shouldCheckGetting(Long id, String name, String rawTags, String rawPath, String expectedResult) {
        ArrayDeque<String> path = new ArrayDeque<>(List.of(rawPath.split("\\.")));
        Set<Tag> tags = Arrays.stream(rawTags.split("\\.")).map(t -> {
            Tag tag = new Tag();
            tag.setName(t);
            return tag;
        }).collect(Collectors.toSet());

        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setTags(tags);
        String result = product.getInDeep(path);

        assertThat(expectedResult).isEqualTo(result);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "shouldCheckGetting_tags.csv", delimiter = '|')
    void shouldCheckGetting_tags(Long id, String name, String rawTags, String rawPath, String rawExpectedResult) {
        ArrayDeque<String> path = new ArrayDeque<>(List.of(rawPath.split("\\.")));
        Set<Tag> tags = Arrays.stream(rawTags.split("\\.")).map(n -> {
            Tag tag = new Tag();
            tag.setName(n);
            return tag;
        }).collect(Collectors.toSet());

        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setTags(tags);

        String result = product.getInDeep(path);
        Set<String> resultTagNames = Arrays.stream(result.split(", ")).collect(Collectors.toSet());
        Set<String> expectedTagNames = Arrays.stream(rawExpectedResult.split(", ")).collect(Collectors.toSet());

        assertThat(expectedTagNames).isEqualTo(resultTagNames);
    }
}