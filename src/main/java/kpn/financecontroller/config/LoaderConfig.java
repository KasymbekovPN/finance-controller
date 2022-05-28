package kpn.financecontroller.config;

import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.data.domains.seller.Seller;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.address.AddressEntity;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.entities.payment.PaymentEntity;
import kpn.financecontroller.data.entities.seller.SellerEntity;
import kpn.financecontroller.data.entities.product.ProductEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.data.services.dto.loaders.Loader;
import kpn.financecontroller.data.services.dto.loaders.LoaderAll;
import kpn.financecontroller.data.services.dto.loaders.LoaderAllAndById;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class LoaderConfig {

    @Bean
    public Loader<Country, CountryEntity, Long> countryLoader(JpaRepository<CountryEntity, Long> repo){
        // TODO: 28.05.2022 toDomains must defined in separated @Configuration
        Function<List<CountryEntity>, List<Country>> toDomains = (entities) -> {
            return entities.stream().map(Country::new).collect(Collectors.toList());
        };
        return new LoaderAllAndById<>(repo, "country", Country::new, toDomains);
    }

    @Bean
    public Loader<Region, RegionEntity, Long> regionLoader(JpaRepository<RegionEntity, Long> repo){
        Function<List<RegionEntity>, List<Region>> toDomains = (entities) -> {
            return entities.stream().map(Region::new).collect(Collectors.toList());
        };
        return new LoaderAllAndById<>(repo, "region", Region::new, toDomains);
    }

    @Bean
    public Loader<City, CityEntity, Long> cityLoader(JpaRepository<CityEntity, Long> repo){
        Function<List<CityEntity>, List<City>> toDomains = (entities) -> {
            return entities.stream().map(City::new).collect(Collectors.toList());
        };
        return new LoaderAllAndById<>(repo, "city", City::new, toDomains);
    }

    @Bean
    public Loader<Street, StreetEntity, Long> streetLoader(JpaRepository<StreetEntity, Long> repo){
        Function<List<StreetEntity>, List<Street>> toDomains = (entities) -> {
            return entities.stream().map(Street::new).collect(Collectors.toList());
        };
        return new LoaderAllAndById<>(repo, "street", Street::new, toDomains);
    }

    @Bean
    public Loader<Address, AddressEntity, Long> addressLoader(JpaRepository<AddressEntity, Long> repo){
        Function<List<AddressEntity>, List<Address>> toDomains = (entities) -> {
            return entities.stream().map(Address::new).collect(Collectors.toList());
        };
        return new LoaderAll<>(repo, "address", Address::new, toDomains);
    }

    @Bean
    public Loader<Product, ProductEntity, Long> productLoader(JpaRepository<ProductEntity, Long> repo){
        Function<List<ProductEntity>, List<Product>> toDomains = (entities) -> {
            return entities.stream().map(Product::new).collect(Collectors.toList());
        };
        return new LoaderAll<>(repo, "product", Product::new, toDomains);
    }

    @Bean
    public Loader<Payment, PaymentEntity, Long> paymentLoader(JpaRepository<PaymentEntity, Long> repo){
        Function<List<PaymentEntity>, List<Payment>> toDomains = (entities) -> {
            return entities.stream().map(Payment::new).collect(Collectors.toList());
        };
        return new LoaderAll<>(repo, "payment", Payment::new, toDomains);
    }

    @Bean
    public Loader<Tag, TagEntity, Long> tagLoader(JpaRepository<TagEntity, Long> repo){
        Function<List<TagEntity>, List<Tag>> toDomains = (entities) -> {
            return entities.stream().map(Tag::new).collect(Collectors.toList());
        };
        return new LoaderAll<>(repo, "tag", Tag::new, toDomains);
    }

    @Bean
    public Loader<Seller, SellerEntity, Long> sellerLoader(JpaRepository<SellerEntity, Long> repo){
        Function<List<SellerEntity>, List<Seller>> toDomains = (entities) -> {
            return entities.stream().map(Seller::new).collect(Collectors.toList());
        };
        return new LoaderAll<>(repo, "seller", Seller::new, toDomains);
    }
}
