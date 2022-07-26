package kpn.financecontroller.config;

import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.domains.seller.Seller;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.lib.ripper.DefaultRipper;
import kpn.lib.ripper.DefaultRipperArg;
import kpn.lib.ripper.Ripper;
import kpn.lib.ripper.RipperArg;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    private Ripper<Seller> createSellerRipper() {
        return DefaultRipper.<Seller>buider()
                .getter(
                        "id",
                        (RipperArg<Seller> arg) -> {
                            Long id = arg.getDomain().getId();
                            return  arg.getPath().isEmpty() && id != null ? Optional.of(String.valueOf(id)) : Optional.empty();
                        }
                )
                .getter(
                        "name",
                        (RipperArg<Seller> arg) -> {
                            String name = arg.getDomain().getName();
                            return  arg.getPath().isEmpty() && name != null ? Optional.of(name) : Optional.empty();
                        }
                )
                .getter(
                        "url",
                        (RipperArg<Seller> arg) -> {
                            String url = arg.getDomain().getUrl();
                            return  arg.getPath().isEmpty() && url != null ? Optional.of(url) : Optional.empty();
                        }
                )
                .getter(
                        "description",
                        (RipperArg<Seller> arg) -> {
                            String description = arg.getDomain().getDescription();
                            return  arg.getPath().isEmpty() && description != null ? Optional.of(description) : Optional.empty();
                        }
                )
                .getter(
                        "address",
                        (RipperArg<Seller> arg) -> {
                            Address address = arg.getDomain().getAddress();
                            Queue<String> path = arg.getPath();
                            return address != null && path.size() > 0
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
                            return  arg.getPath().isEmpty() && id != null ? Optional.of(String.valueOf(id)) : Optional.empty();
                        }
                )
                .getter(
                        "name",
                        (RipperArg<Address> arg) -> {
                            String name = arg.getDomain().getName();
                            return  arg.getPath().isEmpty() && name != null ? Optional.of(name) : Optional.empty();
                        }
                )
                .getter(
                        "street",
                        (RipperArg<Address> arg) -> {
                            Street street = arg.getDomain().getStreet();
                            Queue<String> path = arg.getPath();
                            return street != null && path.size() > 0
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
                            return  arg.getPath().isEmpty() && id != null ? Optional.of(String.valueOf(id)) : Optional.empty();
                        }
                )
                .getter(
                        "name",
                        (RipperArg<Street> arg) -> {
                            String name = arg.getDomain().getName();
                            return  arg.getPath().isEmpty() && name != null ? Optional.of(name) : Optional.empty();
                        }
                )
                .getter(
                        "city",
                        (RipperArg<Street> arg) -> {
                            City city = arg.getDomain().getCity();
                            Queue<String> path = arg.getPath();
                            return city != null && path.size() > 0
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
                            return  arg.getPath().isEmpty() && id != null ? Optional.of(String.valueOf(id)) : Optional.empty();
                        }
                )
                .getter(
                        "name",
                        (RipperArg<City> arg) -> {
                            String name = arg.getDomain().getName();
                            return  arg.getPath().isEmpty() && name != null ? Optional.of(name) : Optional.empty();
                        }
                )
                .getter(
                        "region",
                        (RipperArg<City> arg) -> {
                            Region region = arg.getDomain().getRegion();
                            Queue<String> path = arg.getPath();
                            return region != null && path.size() > 0
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
                            return  arg.getPath().isEmpty() && id != null ? Optional.of(String.valueOf(id)) : Optional.empty();
                        }
                )
                .getter(
                        "name",
                        (RipperArg<Region> arg) -> {
                            String name = arg.getDomain().getName();
                            return  arg.getPath().isEmpty() && name != null ? Optional.of(name) : Optional.empty();
                        }
                )
                .getter(
                        "country",
                        (RipperArg<Region> arg) -> {
                            Country country = arg.getDomain().getCountry();
                            Queue<String> path = arg.getPath();
                            return country != null && path.size() > 0
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
                            return arg.getPath().isEmpty() && id != null ? Optional.of(String.valueOf(id)) : Optional.empty();
                        }
                )
                .getter(
                        "name",
                        (RipperArg<Country> arg) -> {
                            String name = arg.getDomain().getName();
                            return arg.getPath().isEmpty() && name != null ? Optional.of(name) : Optional.empty();
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
                            return arg.getPath().isEmpty() && id != null ? Optional.of(String.valueOf(id)) : Optional.empty();
                        }
                )
                .getter(
                        "name",
                        (RipperArg<Product> arg) -> {
                            String name = arg.getDomain().getName();
                            return arg.getPath().isEmpty() && name != null ? Optional.of(name) : Optional.empty();
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
                            return arg.getPath().isEmpty() && id != null ? Optional.of(String.valueOf(id)) : Optional.empty();
                        }
                )
                .getter(
                        "name",
                        (RipperArg<Tag> arg) -> {
                            String name = arg.getDomain().getName();
                            return arg.getPath().isEmpty() && name != null ? Optional.of(name) : Optional.empty();
                        }
                )
                .build();
    }
}
