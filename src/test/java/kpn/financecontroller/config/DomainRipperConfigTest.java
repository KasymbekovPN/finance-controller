package kpn.financecontroller.config;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.lib.ripper.DefaultRipperArg;
import kpn.lib.ripper.Ripper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class DomainRipperConfigTest {
    private static final Long ID = 1L;
    private static final String TAG_NAME = "tag name";
    private static final String FIRST_PRODUCT_NAME = "first product name";
    private static final String SECOND_PRODUCT_NAME = "second product name";
    private static final String COUNTRY_NAME = "country name";

    @Autowired
    private Ripper<Tag> tagRipper;
    @Autowired
    private Ripper<Product> productRipper;
    @Autowired
    private Ripper<Country> countryRipper;

    @Test
    void shouldCheckTagRipper_idGetting_ifItNull() {
        DefaultRipperArg<Tag> arg = new DefaultRipperArg<>(new Tag(), createPath("id"));
        String result = tagRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckTagRipper_idGetting() {
        Tag tag = new Tag();
        tag.setId(ID);
        DefaultRipperArg<Tag> arg = new DefaultRipperArg<>(tag, createPath("id"));
        String result = tagRipper.run(arg);

        assertThat(result).isEqualTo(String.valueOf(ID));
    }

    @Test
    void shouldCheckTagRipper_nameGetting_ifItNull() {
        DefaultRipperArg<Tag> arg = new DefaultRipperArg<>(new Tag(), createPath("name"));
        String result = tagRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckTagRipper_nameGetting() {
        Tag tag = new Tag();
        tag.setName(TAG_NAME);
        DefaultRipperArg<Tag> arg = new DefaultRipperArg<>(tag, createPath("name"));
        String result = tagRipper.run(arg);

        assertThat(result).isEqualTo(TAG_NAME);
    }

    @Test
    void shouldCheckProductRipper_idGetting_ifItNull() {
        DefaultRipperArg<Product> arg = new DefaultRipperArg<>(new Product(), createPath("id"));
        String result = productRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckProductRipper_idGetting() {
        Product product = new Product();
        product.setId(ID);
        DefaultRipperArg<Product> arg = new DefaultRipperArg<>(product, createPath("id"));
        String result = productRipper.run(arg);

        assertThat(result).isEqualTo(String.valueOf(ID));
    }

    @Test
    void shouldCheckProductRipper_nameGetting_ifItNull() {
        DefaultRipperArg<Product> arg = new DefaultRipperArg<>(new Product(), createPath("name"));
        String result = productRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckProductRipper_nameGetting() {
        Product product = new Product();
        product.setName(FIRST_PRODUCT_NAME);
        DefaultRipperArg<Product> arg = new DefaultRipperArg<>(product, createPath("name"));
        String result = productRipper.run(arg);

        assertThat(result).isEqualTo(FIRST_PRODUCT_NAME);
    }

    @Test
    void shouldCheckProductRipper_tagsGetting_ifItNull() {
        DefaultRipperArg<Product> arg = new DefaultRipperArg<>(new Product(), createPath("tags"));
        String result = productRipper.run(arg);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldCheckProductRipper_tagsGetting() {
        Product product = new Product();
        Set<String> names = Set.of(FIRST_PRODUCT_NAME, SECOND_PRODUCT_NAME);
        product.setTags(
                names.stream().map(n -> {
                    Tag tag = new Tag();
                    tag.setName(n);
                    return tag;
                }).collect(Collectors.toSet())
        );

        DefaultRipperArg<Product> arg = new DefaultRipperArg<>(product, createPath("tags"));
        String result = productRipper.run(arg);

        boolean expectedResult0 = result.equals(FIRST_PRODUCT_NAME + ", " + SECOND_PRODUCT_NAME);
        boolean expectedResult1 = result.equals(SECOND_PRODUCT_NAME + ", " + FIRST_PRODUCT_NAME);
        assertThat(expectedResult0 || expectedResult1).isTrue();
    }


    @Test
    void shouldCheckCountryRipper_idGetting_ifItNull() {
        DefaultRipperArg<Country> arg = new DefaultRipperArg<>(new Country(), createPath("id"));
        String result = countryRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckCountryRipper_idGetting() {
        Country country = new Country();
        country.setId(ID);
        DefaultRipperArg<Country> arg = new DefaultRipperArg<>(country, createPath("id"));
        String result = countryRipper.run(arg);

        assertThat(result).isEqualTo(String.valueOf(ID));
    }

    @Test
    void shouldCheckCountryRipper_nameGetting_ifItNull() {
        DefaultRipperArg<Country> arg = new DefaultRipperArg<>(new Country(), createPath("name"));
        String result = countryRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckCountryRipper_nameGetting() {
        Country country = new Country();
        country.setName(COUNTRY_NAME);
        DefaultRipperArg<Country> arg = new DefaultRipperArg<>(country, createPath("name"));
        String result = countryRipper.run(arg);

        assertThat(result).isEqualTo(COUNTRY_NAME);
    }

    private Queue<String> createPath(String... path) {
        return new ArrayDeque<>(List.of(path));
    }
}