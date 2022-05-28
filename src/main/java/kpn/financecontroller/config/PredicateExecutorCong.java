package kpn.financecontroller.config;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.domains.seller.Seller;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.address.AddressEntity;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.entities.payment.PaymentEntity;
import kpn.financecontroller.data.entities.product.ProductEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.entities.seller.SellerEntity;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.data.services.dto.executors.PredicateExecutor;
import kpn.financecontroller.data.services.dto.executors.PredicateExecutorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class PredicateExecutorCong {

    @Bean
    public PredicateExecutor<Country, Predicate> countryPredicateExecutor(QuerydslPredicateExecutor<CountryEntity> repo){
        // TODO: 28.05.2022 toDomains must defined in separated @Configuration
        Function<List<CountryEntity>, List<Country>> toDomains = (entities) -> {
            return entities.stream().map(Country::new).collect(Collectors.toList());
        };
        return new PredicateExecutorImpl<>("country", repo, toDomains);
    }

    @Bean
    public PredicateExecutor<Region, Predicate> regionPredicateExecutor(QuerydslPredicateExecutor<RegionEntity> repo){
        Function<List<RegionEntity>, List<Region>> toDomains = (entities) -> {
            return entities.stream().map(Region::new).collect(Collectors.toList());
        };
        return new PredicateExecutorImpl<>("region", repo, toDomains);
    }

    @Bean
    public PredicateExecutor<City, Predicate> cityPredicateExecutor(QuerydslPredicateExecutor<CityEntity> repo){
        Function<List<CityEntity>, List<City>> toDomains = (entities) -> {
            return entities.stream().map(City::new).collect(Collectors.toList());
        };
        return new PredicateExecutorImpl<>("city", repo, toDomains);
    }

    @Bean
    public PredicateExecutor<Street, Predicate> streetPredicateExecutor(QuerydslPredicateExecutor<StreetEntity> repo){
        Function<List<StreetEntity>, List<Street>> toDomains = (entities) -> {
            return entities.stream().map(Street::new).collect(Collectors.toList());
        };
        return new PredicateExecutorImpl<>("street", repo, toDomains);
    }

    @Bean
    public PredicateExecutor<Address, Predicate> addressPredicateExecutor(QuerydslPredicateExecutor<AddressEntity> repo){
        Function<List<AddressEntity>, List<Address>> toDomains = (entities) -> {
            return entities.stream().map(Address::new).collect(Collectors.toList());
        };
        return new PredicateExecutorImpl<>("address", repo, toDomains);
    }

    @Bean
    public PredicateExecutor<Seller, Predicate> sellerPredicateExecutor(QuerydslPredicateExecutor<SellerEntity> repo){
        Function<List<SellerEntity>, List<Seller>> toDomains = (entities) -> {
            return entities.stream().map(Seller::new).collect(Collectors.toList());
        };
        return new PredicateExecutorImpl<>("seller", repo, toDomains);
    }

    @Bean
    public PredicateExecutor<Payment, Predicate> paymentPredicateExecutor(QuerydslPredicateExecutor<PaymentEntity> repo){
        Function<List<PaymentEntity>, List<Payment>> toDomains = (entities) -> {
            return entities.stream().map(Payment::new).collect(Collectors.toList());
        };
        return new PredicateExecutorImpl<>("payment", repo, toDomains);
    }

    @Bean
    public PredicateExecutor<Tag, Predicate> tagPredicateExecutor(QuerydslPredicateExecutor<TagEntity> repo){
        Function<List<TagEntity>, List<Tag>> toDomains = (entities) -> {
            return entities.stream().map(Tag::new).collect(Collectors.toList());
        };
        return new PredicateExecutorImpl<>("tag", repo, toDomains);
    }

    @Bean
    public PredicateExecutor<Product, Predicate> productPredicateExecutor(QuerydslPredicateExecutor<ProductEntity> repo){
        Function<List<ProductEntity>, List<Product>> toDomains = (entities) -> {
            return entities.stream().map(Product::new).collect(Collectors.toList());
        };
        return new PredicateExecutorImpl<>("product", repo, toDomains);
    }
}
