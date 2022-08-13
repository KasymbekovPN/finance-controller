package kpn.financecontroller.config;

import kpn.financecontroller.data.domain.*;
import kpn.financecontroller.data.domain.auxi.Currency;
import kpn.financecontroller.data.domain.auxi.Measure;
import kpn.lib.domain.Domain;
import kpn.lib.ripper.DefaultRipper;
import kpn.lib.ripper.DefaultRipperArg;
import kpn.lib.ripper.Ripper;
import kpn.lib.ripper.RipperArg;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Queue;

@Configuration
public class DomainRipperConfig {

    @Bean
    public Ripper<Tag> tagRipper(){
        return createTagRipper();
    }

    @Bean
    public Ripper<Product> productRipper(){
        return createProductRipper();
    }

    @Bean
    public Ripper<Country> countryRipper(){
        return  createCountryRipper();
    }

    @Bean
    public Ripper<Region> regionRipper(){
        return createRegionRipper();
    }

    @Bean
    public Ripper<City> cityRipper(){
        return createCityRipper();
    }

    @Bean
    public Ripper<Street> streetRipper(){
        return createStreetRipper();
    }

    @Bean
    public Ripper<Address> addressRipper(){
        return createAddressRipper();
    }

    @Bean
    public Ripper<Seller> sellerRipper(){
        return  createSellerRipper();
    }

    @Bean
    public Ripper<Payment> paymentRipper(){
        return DefaultRipper.<Payment>buider()
                .getter(
                        "id",
                        (RipperArg<Payment> arg) -> {
                            Long id = arg.getDomain().getId();
                            return doCommonHandling(arg, id);
                        }
                )
                .getter(
                        "price",
                        (RipperArg<Payment> arg) -> {
                            Float price = arg.getDomain().getPrice();
                            return doCommonHandling(arg, price);
                        }
                )
                .getter(
                        "currency",
                        (RipperArg<Payment> arg) -> {
                            Currency currency = arg.getDomain().getCurrency();
                            return doCommonHandling(arg, currency);
                        }
                )
                .getter(
                        "amount",
                        (RipperArg<Payment> arg) -> {
                            Float amount = arg.getDomain().getAmount();
                            return doCommonHandling(arg, amount);
                        }
                )
                .getter(
                        "measure",
                        (RipperArg<Payment> arg) -> {
                            Measure measure = arg.getDomain().getMeasure();
                            return doCommonHandling(arg, measure);
                        }
                )
                .getter(
                        "createdAt",
                        (RipperArg<Payment> arg) -> {
                            LocalDate createdAt = arg.getDomain().getCreatedAt();
                            return doCommonHandling(arg, createdAt);
                        }
                )
                .getter(
                        "product",
                        (RipperArg<Payment> arg) -> {
                            Product product = arg.getDomain().getProduct();
                            Queue<String> path = arg.getPath();
                            return !path.isEmpty() && product != null
                                    ? Optional.of(createProductRipper().run(new DefaultRipperArg<>(product, path)))
                                    : Optional.empty();
                        }
                )
                .getter(
                        "seller",
                        (RipperArg<Payment> arg) -> {
                            Seller seller = arg.getDomain().getSeller();
                            Queue<String> path = arg.getPath();
                            return !path.isEmpty() && seller != null
                                    ? Optional.of(createSellerRipper().run(new DefaultRipperArg<>(seller, path)))
                                    : Optional.empty();
                        }
                )
                .build();
    }

    @Bean
    public Ripper<Action> actionRipper(){
        return DefaultRipper.<Action>buider()
                .getter(
                        "id",
                        (RipperArg<Action> arg) -> {
                            Long id = arg.getDomain().getId();
                            return doCommonHandling(arg, id);
                        }
                )
                .getter(
                        "description",
                        (RipperArg<Action> arg) -> {
                            String description = arg.getDomain().getDescription();
                            return doCommonHandling(arg, description);
                        }
                )
                .build();
    }
    private Ripper<Seller> createSellerRipper() {
        return DefaultRipper.<Seller>buider()
                .getter(
                        "id",
                        (RipperArg<Seller> arg) -> {
                            Long id = arg.getDomain().getId();
                            return doCommonHandling(arg, id);
                        }
                )
                .getter(
                        "name",
                        (RipperArg<Seller> arg) -> {
                            String name = arg.getDomain().getName();
                            return doCommonHandling(arg, name);
                        }
                )
                .getter(
                        "url",
                        (RipperArg<Seller> arg) -> {
                            String url = arg.getDomain().getUrl();
                            return doCommonHandling(arg, url);
                        }
                )
                .getter(
                        "description",
                        (RipperArg<Seller> arg) -> {
                            String description = arg.getDomain().getDescription();
                            return doCommonHandling(arg, description);
                        }
                )
                .getter(
                        "address",
                        (RipperArg<Seller> arg) -> {
                            Address address = arg.getDomain().getAddress();
                            Queue<String> path = arg.getPath();
                            return address != null && !path.isEmpty()
                                    ? Optional.of(createAddressRipper().run(new DefaultRipperArg<>(address, path)))
                                    : Optional.empty();
                        }
                )
                .build();
    }

    private Ripper<Address> createAddressRipper() {
        return DefaultRipper.<Address>buider()
                .getter(
                        "id",
                        (RipperArg<Address> arg) -> {
                            Long id = arg.getDomain().getId();
                            return doCommonHandling(arg, id);
                        }
                )
                .getter(
                        "name",
                        (RipperArg<Address> arg) -> {
                            String name = arg.getDomain().getName();
                            return doCommonHandling(arg, name);
                        }
                )
                .getter(
                        "street",
                        (RipperArg<Address> arg) -> {
                            Street street = arg.getDomain().getStreet();
                            Queue<String> path = arg.getPath();
                            return street != null && !path.isEmpty()
                                    ? Optional.of(createStreetRipper().run(new DefaultRipperArg<>(street, path)))
                                    : Optional.empty();
                        }
                )
                .build();
    }

    private Ripper<Street> createStreetRipper() {
        return DefaultRipper.<Street>buider()
                .getter(
                        "id",
                        (RipperArg<Street> arg) -> {
                            Long id = arg.getDomain().getId();
                            return doCommonHandling(arg, id);
                        }
                )
                .getter(
                        "name",
                        (RipperArg<Street> arg) -> {
                            String name = arg.getDomain().getName();
                            return doCommonHandling(arg, name);
                        }
                )
                .getter(
                        "city",
                        (RipperArg<Street> arg) -> {
                            City city = arg.getDomain().getCity();
                            Queue<String> path = arg.getPath();
                            return city != null && !path.isEmpty()
                                    ? Optional.of(createCityRipper().run(new DefaultRipperArg<>(city, path)))
                                    : Optional.empty();
                        }
                )
                .build();
    }

    private Ripper<City> createCityRipper() {
        return DefaultRipper.<City>buider()
                .getter(
                        "id",
                        (RipperArg<City> arg) -> {
                            Long id = arg.getDomain().getId();
                            return doCommonHandling(arg, id);
                        }
                )
                .getter(
                        "name",
                        (RipperArg<City> arg) -> {
                            String name = arg.getDomain().getName();
                            return doCommonHandling(arg, name);
                        }
                )
                .getter(
                        "region",
                        (RipperArg<City> arg) -> {
                            Region region = arg.getDomain().getRegion();
                            Queue<String> path = arg.getPath();
                            return region != null && !path.isEmpty()
                                    ? Optional.of(createRegionRipper().run(new DefaultRipperArg<>(region, path)))
                                    : Optional.empty();
                        }
                )
                .build();
    }

    private Ripper<Region> createRegionRipper() {
        return DefaultRipper.<Region>buider()
                .getter(
                        "id",
                        (RipperArg<Region> arg) -> {
                            Long id = arg.getDomain().getId();
                            return doCommonHandling(arg, id);
                        }
                )
                .getter(
                        "name",
                        (RipperArg<Region> arg) -> {
                            String name = arg.getDomain().getName();
                            return doCommonHandling(arg, name);
                        }
                )
                .getter(
                        "country",
                        (RipperArg<Region> arg) -> {
                            Country country = arg.getDomain().getCountry();
                            Queue<String> path = arg.getPath();
                            return country != null && !path.isEmpty()
                                    ? Optional.of(createCountryRipper().run(new DefaultRipperArg<>(country, path)))
                                    : Optional.empty();
                        }
                )
                .build();
    }

    private Ripper<Country> createCountryRipper() {
        return DefaultRipper.<Country>buider()
                .getter(
                        "id",
                        (RipperArg<Country> arg) -> {
                            Long id = arg.getDomain().getId();
                            return doCommonHandling(arg, id);
                        }
                )
                .getter(
                        "name",
                        (RipperArg<Country> arg) -> {
                            String name = arg.getDomain().getName();
                            return doCommonHandling(arg, name);
                        }
                )
                .build();
    }

    private Ripper<Product> createProductRipper() {
        return DefaultRipper.<Product>buider()
                .getter(
                        "id",
                        (RipperArg<Product> arg) -> {
                            Long id = arg.getDomain().getId();
                            return doCommonHandling(arg, id);
                        }
                )
                .getter(
                        "name",
                        (RipperArg<Product> arg) -> {
                            String name = arg.getDomain().getName();
                            return doCommonHandling(arg, name);
                        }
                )
                .getter(
                        "tags",
                        (RipperArg<Product> arg) -> {
                            return arg.getPath().isEmpty() ? Optional.of(arg.getDomain().getInfo()) : Optional.empty();
                        }
                )
                .build();
    }

    private Ripper<Tag> createTagRipper() {
        return DefaultRipper.<Tag>buider()
                .getter(
                        "id",
                        (RipperArg<Tag> arg) -> {
                            Long id = arg.getDomain().getId();
                            return doCommonHandling(arg, id);
                        }
                )
                .getter(
                        "name",
                        (RipperArg<Tag> arg) -> {
                            String name = arg.getDomain().getName();
                            return doCommonHandling(arg, name);
                        }
                )
                .build();
    }

    private <D extends Domain<?>> Optional<String> doCommonHandling(RipperArg<D> arg, Object obj){
        return arg.getPath().isEmpty() && obj != null
                ? Optional.of(String.valueOf(obj))
                : Optional.empty();
    }
}
