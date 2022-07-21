package kpn.financecontroller.rfunc.checker.saving;

import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BeforeProductSavingCheckerTest {

    private static final String EMPTY_NAME = "";
    private static final String NAME = "name";

    @Test
    void shouldCheck_whenNameNull() {
        Product product = new Builder().build();
        ImmutableResult<List<Product>> expectedResult = ImmutableResult.<List<Product>>fail("checking.domain.product.name.isEmpty");

        Result<List<Product>> result = new BeforeProductSavingChecker().apply(product);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenNameEmpty() {
        Product product = new Builder().name(EMPTY_NAME).build();
        ImmutableResult<List<Product>> expectedResult = ImmutableResult.<List<Product>>fail("checking.domain.product.name.isEmpty");

        Result<List<Product>> result = new BeforeProductSavingChecker().apply(product);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenTagsNull() {
        Product product = new Builder().name(NAME).build();
        ImmutableResult<List<Product>> expectedResult = ImmutableResult.<List<Product>>fail("checking.domain.product.tags.isEmpty");

        Result<List<Product>> result = new BeforeProductSavingChecker().apply(product);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck_whenTagsEmpty() {
        Product product = new Builder().name(NAME).createTags().build();
        ImmutableResult<List<Product>> expectedResult = ImmutableResult.<List<Product>>fail("checking.domain.product.tags.isEmpty");

        Result<List<Product>> result = new BeforeProductSavingChecker().apply(product);
        assertThat(expectedResult).isEqualTo(result);
    }

    @Test
    void shouldCheck() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("tag");

        Product product = new Builder().name(NAME).createTags().tag(tag).build();
        ImmutableResult<List<Product>> expectedResult = ImmutableResult.<List<Product>>ok(List.of(product));

        Result<List<Product>> result = new BeforeProductSavingChecker().apply(product);
        assertThat(expectedResult).isEqualTo(result);
    }

    private static class Builder{
        private String name;
        private HashSet<Tag> tags;

        private Product build(){
            Product product = new Product();
            product.setName(name);
            product.setTags(tags);
            return product;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder createTags() {
            this.tags = new HashSet<>();
            return this;
        }

        public Builder tag(Tag tag) {
            if (tags != null){
                tags.add(tag);
            }
            return this;
        }
    }
}