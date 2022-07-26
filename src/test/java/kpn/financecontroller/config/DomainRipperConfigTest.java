package kpn.financecontroller.config;

import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.domains.seller.Seller;
import kpn.financecontroller.data.domains.street.Street;
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
    private static final String REGION_NAME = "region name";
    private static final String CITY_NAME = "city name";
    private static final String STREET_NAME = "street name";
    private static final String ADDRESS_NAME = "address name";
    private static final String SELLER_NAME = "seller name";
    private static final String SELLER_URL = "seller url";
    private static final String SELLER_DESCRIPTION = "seller description";

    @Autowired
    private Ripper<Tag> tagRipper;
    @Autowired
    private Ripper<Product> productRipper;
    @Autowired
    private Ripper<Country> countryRipper;
    @Autowired
    private Ripper<Region> regionRiper;
    @Autowired
    private Ripper<City> cityRipper;
    @Autowired
    private Ripper<Street> streetRipper;
    @Autowired
    private Ripper<Address> addressRipper;
    @Autowired
    private Ripper<Seller> sellerRipper;

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

    @Test
    void shouldCheckRegionRipper_idGetting_ifNull() {
        DefaultRipperArg<Region> arg = new DefaultRipperArg<>(new Region(), createPath("id"));
        String result = regionRiper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckRegionRipper_idGetting() {
        Region region = new Region();
        region.setId(ID);
        DefaultRipperArg<Region> arg = new DefaultRipperArg<>(region, createPath("id"));
        String result = regionRiper.run(arg);

        assertThat(result).isEqualTo(String.valueOf(ID));
    }

    @Test
    void shouldCheckRegionRipper_nameGetting_ifNull() {
        DefaultRipperArg<Region> arg = new DefaultRipperArg<>(new Region(), createPath("name"));
        String result = regionRiper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckRegionRipper_nameGetting() {
        Region region = new Region();
        region.setName(REGION_NAME);
        DefaultRipperArg<Region> arg = new DefaultRipperArg<>(region, createPath("name"));
        String result = regionRiper.run(arg);

        assertThat(result).isEqualTo(REGION_NAME);
    }

    @Test
    void shouldCheckRegionRipper_countryNameGetting_ifNull() {
        DefaultRipperArg<Region> arg = new DefaultRipperArg<>(new Region(), createPath("country", "name"));
        String result = regionRiper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckRegionRipper_countryNameGetting() {
        Country country = new Country();
        country.setName(COUNTRY_NAME);
        Region region = new Region();
        region.setCountry(country);
        DefaultRipperArg<Region> arg = new DefaultRipperArg<>(region, createPath("country", "name"));
        String result = regionRiper.run(arg);

        assertThat(result).isEqualTo(COUNTRY_NAME);
    }

    @Test
    void shouldCheckCityRipper_idGetting_ifNull() {
        DefaultRipperArg<City> arg = new DefaultRipperArg<>(new City(), createPath("id"));
        String result = cityRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckCityRipper_idGetting() {
        City city = new City();
        city.setId(ID);
        DefaultRipperArg<City> arg = new DefaultRipperArg<>(city, createPath("id"));
        String result = cityRipper.run(arg);

        assertThat(result).isEqualTo(String.valueOf(ID));
    }

    @Test
    void shouldCheckCityRipper_nameGetting_ifNull() {
        DefaultRipperArg<City> arg = new DefaultRipperArg<>(new City(), createPath("name"));
        String result = cityRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckCityRipper_nameGetting() {
        City city = new City();
        city.setName(CITY_NAME);
        DefaultRipperArg<City> arg = new DefaultRipperArg<>(city, createPath("name"));
        String result = cityRipper.run(arg);

        assertThat(result).isEqualTo(CITY_NAME);
    }

    @Test
    void shouldCheckCityRipper_countryNameGetting_ifNull() {
        DefaultRipperArg<City> arg = new DefaultRipperArg<>(new City(), createPath("region", "name"));
        String result = cityRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckCityRipper_regionNameGetting() {
        Region region = new Region();
        region.setName(REGION_NAME);
        City city = new City();
        city.setRegion(region);
        DefaultRipperArg<City> arg = new DefaultRipperArg<>(city, createPath("region", "name"));
        String result = cityRipper.run(arg);

        assertThat(result).isEqualTo(REGION_NAME);
    }

    @Test
    void shouldCheckStreetRipper_idGetting_ifNull() {
        DefaultRipperArg<Street> arg = new DefaultRipperArg<>(new Street(), createPath("id"));
        String result = streetRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckStreetRipper_idGetting() {
        Street street = new Street();
        street.setId(ID);
        DefaultRipperArg<Street> arg = new DefaultRipperArg<>(street, createPath("id"));
        String result = streetRipper.run(arg);

        assertThat(result).isEqualTo(String.valueOf(ID));
    }

    @Test
    void shouldCheckStreetRipper_nameGetting_ifNull() {
        DefaultRipperArg<Street> arg = new DefaultRipperArg<>(new Street(), createPath("name"));
        String result = streetRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckStreetRipper_nameGetting() {
        Street street = new Street();
        street.setName(STREET_NAME);
        DefaultRipperArg<Street> arg = new DefaultRipperArg<>(street, createPath("name"));
        String result = streetRipper.run(arg);

        assertThat(result).isEqualTo(STREET_NAME);
    }

    @Test
    void shouldCheckStreetRipper_cityNameGetting_ifNull() {
        DefaultRipperArg<Street> arg = new DefaultRipperArg<>(new Street(), createPath("city", "name"));
        String result = streetRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckStreetRipper_cityNameGetting() {
        City city = new City();
        city.setName(CITY_NAME);
        Street street = new Street();
        street.setCity(city);
        DefaultRipperArg<Street> arg = new DefaultRipperArg<>(street, createPath("city", "name"));
        String result = streetRipper.run(arg);

        assertThat(result).isEqualTo(CITY_NAME);
    }

    @Test
    void shouldCheckAddressRipper_idGetting_ifNull() {
        DefaultRipperArg<Address> arg = new DefaultRipperArg<>(new Address(), createPath("id"));
        String result = addressRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckAddressRipper_idGetting() {
        Address address = new Address();
        address.setId(ID);
        DefaultRipperArg<Address> arg = new DefaultRipperArg<>(address, createPath("id"));
        String result = addressRipper.run(arg);

        assertThat(result).isEqualTo(String.valueOf(ID));
    }

    @Test
    void shouldCheckAddressRipper_nameGetting_ifNull() {
        DefaultRipperArg<Address> arg = new DefaultRipperArg<>(new Address(), createPath("name"));
        String result = addressRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckAddressRipper_nameGetting() {
        Address address = new Address();
        address.setName(ADDRESS_NAME);
        DefaultRipperArg<Address> arg = new DefaultRipperArg<>(address, createPath("name"));
        String result = addressRipper.run(arg);

        assertThat(result).isEqualTo(ADDRESS_NAME);
    }

    @Test
    void shouldCheckAddressRipper_streetNameGetting_ifNull() {
        DefaultRipperArg<Address> arg = new DefaultRipperArg<>(new Address(), createPath("city", "name"));
        String result = addressRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckAddressRipper_streetNameGetting() {
        Street street = new Street();
        street.setName(STREET_NAME);
        Address address = new Address();
        address.setStreet(street);
        DefaultRipperArg<Address> arg = new DefaultRipperArg<>(address, createPath("street", "name"));
        String result = addressRipper.run(arg);

        assertThat(result).isEqualTo(STREET_NAME);
    }

    @Test
    void shouldCheckSellerRipper_idGetting_ifNull() {
        DefaultRipperArg<Seller> arg = new DefaultRipperArg<>(new Seller(), createPath("id"));
        String result = sellerRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckSellerRipper_idGetting() {
        Seller seller = new Seller();
        seller.setId(ID);
        DefaultRipperArg<Seller> arg = new DefaultRipperArg<>(seller, createPath("id"));
        String result = sellerRipper.run(arg);

        assertThat(result).isEqualTo(String.valueOf(ID));
    }

    @Test
    void shouldCheckSellerRipper_nameGetting_ifNull() {
        DefaultRipperArg<Seller> arg = new DefaultRipperArg<>(new Seller(), createPath("name"));
        String result = sellerRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckSellerRipper_nameGetting() {
        Seller seller = new Seller();
        seller.setName(SELLER_NAME);
        DefaultRipperArg<Seller> arg = new DefaultRipperArg<>(seller, createPath("name"));
        String result = sellerRipper.run(arg);

        assertThat(result).isEqualTo(SELLER_NAME);
    }

    @Test
    void shouldCheckSellerRipper_urlGetting_ifNull() {
        DefaultRipperArg<Seller> arg = new DefaultRipperArg<>(new Seller(), createPath("url"));
        String result = sellerRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckSellerRipper_urlGetting() {
        Seller seller = new Seller();
        seller.setUrl(SELLER_URL);
        DefaultRipperArg<Seller> arg = new DefaultRipperArg<>(seller, createPath("url"));
        String result = sellerRipper.run(arg);

        assertThat(result).isEqualTo(SELLER_URL);
    }

    @Test
    void shouldCheckSellerRipper_descriptionGetting_ifNull() {
        DefaultRipperArg<Seller> arg = new DefaultRipperArg<>(new Seller(), createPath("description"));
        String result = sellerRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckSellerRipper_descriptionGetting() {
        Seller seller = new Seller();
        seller.setDescription(SELLER_DESCRIPTION);
        DefaultRipperArg<Seller> arg = new DefaultRipperArg<>(seller, createPath("description"));
        String result = sellerRipper.run(arg);

        assertThat(result).isEqualTo(SELLER_DESCRIPTION);
    }

    @Test
    void shouldCheckSellerRipper_addressNameGetting_ifNull() {
        DefaultRipperArg<Seller> arg = new DefaultRipperArg<>(new Seller(), createPath("address", "name"));
        String result = sellerRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckSellerRipper_addressNameGetting() {
        Address address = new Address();
        address.setName(ADDRESS_NAME);
        Seller seller = new Seller();
        seller.setAddress(address);
        DefaultRipperArg<Seller> arg = new DefaultRipperArg<>(seller, createPath("address", "name"));
        String result = sellerRipper.run(arg);

        assertThat(result).isEqualTo(ADDRESS_NAME);
    }

    private Queue<String> createPath(String... path) {
        return new ArrayDeque<>(List.of(path));
    }
}