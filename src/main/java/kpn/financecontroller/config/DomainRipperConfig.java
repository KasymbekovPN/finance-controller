package kpn.financecontroller.config;

import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.lib.ripper.DefaultRipper;
import kpn.lib.ripper.Ripper;
import kpn.lib.ripper.RipperArg;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

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
