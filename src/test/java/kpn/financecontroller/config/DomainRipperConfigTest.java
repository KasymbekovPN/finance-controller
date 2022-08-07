package kpn.financecontroller.config;

import kpn.financecontroller.data.domain.Address;
import kpn.financecontroller.data.domain.City;
import kpn.financecontroller.data.domain.Country;
import kpn.financecontroller.data.domain.auxi.Currency;
import kpn.financecontroller.data.domain.auxi.Measure;
import kpn.financecontroller.data.domain.Payment;
import kpn.financecontroller.data.domain.Product;
import kpn.financecontroller.data.domain.Region;
import kpn.financecontroller.data.domain.Seller;
import kpn.financecontroller.data.domain.Street;
import kpn.financecontroller.data.domain.Tag;
import kpn.lib.ripper.DefaultRipperArg;
import kpn.lib.ripper.Ripper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class DomainRipperConfigTest {
    private static final Long ID = 1L;
    private static final String FIRST_TAG_NAME = "first tag name";
    private static final String SECOND_TAG_NAME = "second product name";
    private static final String PRODUCT_NAME = "product name";
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
    @Autowired
    private Ripper<Payment> paymentRipper;

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
        tag.setName(FIRST_TAG_NAME);
        DefaultRipperArg<Tag> arg = new DefaultRipperArg<>(tag, createPath("name"));
        String result = tagRipper.run(arg);

        assertThat(result).isEqualTo(FIRST_TAG_NAME);
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
        product.setName(PRODUCT_NAME);
        DefaultRipperArg<Product> arg = new DefaultRipperArg<>(product, createPath("name"));
        String result = productRipper.run(arg);

        assertThat(result).isEqualTo(PRODUCT_NAME);
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
        Set<String> names = Set.of(FIRST_TAG_NAME, SECOND_TAG_NAME);
        product.setTags(
                names.stream().map(n -> {
                    Tag tag = new Tag();
                    tag.setName(n);
                    return tag;
                }).collect(Collectors.toSet())
        );

        DefaultRipperArg<Product> arg = new DefaultRipperArg<>(product, createPath("tags"));
        String result = productRipper.run(arg);

        boolean expectedResult0 = result.equals(FIRST_TAG_NAME + ", " + SECOND_TAG_NAME);
        boolean expectedResult1 = result.equals(SECOND_TAG_NAME + ", " + FIRST_TAG_NAME);
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

    @Test
    void shouldCheckPaymentRipper_idGetting_ifNull() {
        DefaultRipperArg<Payment> arg = new DefaultRipperArg<>(new Payment(), createPath("id"));
        String result = paymentRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckPaymentRipper_idGetting() {
        Payment payment = new Payment();
        payment.setId(ID);
        DefaultRipperArg<Payment> arg = new DefaultRipperArg<>(payment, createPath("id"));
        String result = paymentRipper.run(arg);

        assertThat(result).isEqualTo(String.valueOf(ID));
    }

    @Test
    void shouldCheckPaymentRipper_amountGetting_ifNull() {
        DefaultRipperArg<Payment> arg = new DefaultRipperArg<>(new Payment(), createPath("id"));
        String result = paymentRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckPaymentRipper_amountGetting() {
        Payment payment = new Payment();
        float expectedAmount = 123.45f;
        payment.setAmount(expectedAmount);
        DefaultRipperArg<Payment> arg = new DefaultRipperArg<>(payment, createPath("amount"));
        String result = paymentRipper.run(arg);

        assertThat(result).isEqualTo(String.valueOf(expectedAmount));
    }

    @Test
    void shouldCheckPaymentRipper_measureGetting_ifNull() {
        DefaultRipperArg<Payment> arg = new DefaultRipperArg<>(new Payment(), createPath("measure"));
        String result = paymentRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckPaymentRipper_measureGetting() {
        Payment payment = new Payment();
        Measure measure = Measure.KG;
        payment.setMeasure(measure);
        DefaultRipperArg<Payment> arg = new DefaultRipperArg<>(payment, createPath("measure"));
        String result = paymentRipper.run(arg);

        assertThat(result).isEqualTo(measure.name());
    }

    @Test
    void shouldCheckPaymentRipper_priceGetting_ifNull() {
        DefaultRipperArg<Payment> arg = new DefaultRipperArg<>(new Payment(), createPath("price"));
        String result = paymentRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckPaymentRipper_priceGetting() {
        Payment payment = new Payment();
        float expectedPrice = 34.56f;
        payment.setPrice(expectedPrice);
        DefaultRipperArg<Payment> arg = new DefaultRipperArg<>(payment, createPath("price"));
        String result = paymentRipper.run(arg);

        assertThat(result).isEqualTo(String.valueOf(expectedPrice));
    }

    @Test
    void shouldCheckPaymentRipper_currencyGetting_ifNull() {
        DefaultRipperArg<Payment> arg = new DefaultRipperArg<>(new Payment(), createPath("currency"));
        String result = paymentRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckPaymentRipper_currencyGetting() {
        Payment payment = new Payment();
        Currency expectedCurrency = Currency.RUB;
        payment.setCurrency(expectedCurrency);
        DefaultRipperArg<Payment> arg = new DefaultRipperArg<>(payment, createPath("currency"));
        String result = paymentRipper.run(arg);

        assertThat(result).isEqualTo(expectedCurrency.name());
    }

    @Test
    void shouldCheckPaymentRipper_createdAtGetting_ifNull() {
        DefaultRipperArg<Payment> arg = new DefaultRipperArg<>(new Payment(), createPath("createdAt"));
        String result = paymentRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckPaymentRipper_createdAtGetting() {
        Payment payment = new Payment();
        LocalDate expectedTime = LocalDate.now();
        payment.setCreatedAt(expectedTime);
        DefaultRipperArg<Payment> arg = new DefaultRipperArg<>(payment, createPath("createdAt"));
        String result = paymentRipper.run(arg);

        assertThat(result).isEqualTo(String.valueOf(expectedTime));
    }

    @Test
    void shouldCheckPaymentRipper_productGetting_ifNull() {
        DefaultRipperArg<Payment> arg = new DefaultRipperArg<>(new Payment(), createPath("product", "name"));
        String result = paymentRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }

    @Test
    void shouldCheckPaymentRipper_productGetting() {
        Product product = new Product();
        product.setName(PRODUCT_NAME);
        Payment payment = new Payment();
        payment.setProduct(product);
        DefaultRipperArg<Payment> arg = new DefaultRipperArg<>(payment, createPath("product", "name"));
        String result = paymentRipper.run(arg);

        assertThat(result).isEqualTo(PRODUCT_NAME);
    }

    @Test
    void shouldCheckPaymentRipper_sellerGetting_ifNull() {
        DefaultRipperArg<Payment> arg = new DefaultRipperArg<>(new Payment(), createPath("seller", "name"));
        String result = paymentRipper.run(arg);

        assertThat(result).isEqualTo("-");
    }
    @Test
    void shouldCheckPaymentRipper_sellerGetting() {
        Seller seller = new Seller();
        seller.setName(SELLER_NAME);
        Payment payment = new Payment();
        payment.setSeller(seller);
        DefaultRipperArg<Payment> arg = new DefaultRipperArg<>(payment, createPath("seller", "name"));
        String result = paymentRipper.run(arg);

        assertThat(result).isEqualTo(SELLER_NAME);
    }


    private Queue<String> createPath(String... path) {
        return new ArrayDeque<>(List.of(path));
    }
}