package kpn.financecontroller.config;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.converters.aspect.FromAspectConverter;
import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.data.domains.city.City;
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
import kpn.financecontroller.data.repos.address.AddressRepo;
import kpn.financecontroller.data.repos.city.CityRepo;
import kpn.financecontroller.data.repos.country.CountryRepo;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.repos.payment.PaymentRepo;
import kpn.financecontroller.data.repos.product.ProductRepo;
import kpn.financecontroller.data.repos.region.RegionRepo;
import kpn.financecontroller.data.repos.seller.SellerRepo;
import kpn.financecontroller.data.repos.street.StreetRepo;
import kpn.financecontroller.data.repos.tag.TagRepo;
import kpn.financecontroller.data.services.dto.deleters.ByIdDeletingExecutorImpl;
import kpn.financecontroller.data.services.dto.deleters.CompletelyDeletingExecutorImpl;
import kpn.financecontroller.data.services.dto.executors.PredicateExecutorImpl;
import kpn.financecontroller.data.services.dto.loaders.ByIdLoadingExecutorImpl;
import kpn.financecontroller.data.services.dto.loaders.CompletelyLoadingExecutorImpl;
import kpn.financecontroller.data.services.dto.savers.SavingExecutorImpl;
import kpn.lib.buider.ServiceBuider;
import kpn.lib.domain.Domain;
import kpn.lib.entity.Entity;
import kpn.lib.result.Result;
import kpn.lib.service.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.function.Function;

@Configuration
public class DTOServiceConfig {

    @Bean
    public Service<Long, Country, Predicate, Result<List<Country>>> countryService(CountryRepo repo){
        return createService("country", repo, repo, Country::new, CountryEntity::new);
    }

    @Bean
    public Service<Long, Region, Predicate, Result<List<Region>>> regionService(RegionRepo repo){
        return createService("region", repo, repo, Region::new, RegionEntity::new);
    }

    @Bean
    public Service<Long, City, Predicate, Result<List<City>>> cityService(CityRepo repo){
        return createService("city", repo, repo, City::new, CityEntity::new);
    }

    @Bean
    public Service<Long, Street, Predicate, Result<List<Street>>> streetService(StreetRepo repo){
        return createService("street", repo, repo, Street::new, StreetEntity::new);
    }

    @Bean
    public Service<Long, Address, Predicate, Result<List<Address>>> addressService(AddressRepo repo){
        return createService("address", repo, repo, Address::new, AddressEntity::new);
    }

    @Bean
    public Service<Long, Seller, Predicate, Result<List<Seller>>> sellerService(SellerRepo repo){
        return createService("seller", repo, repo, Seller::new, SellerEntity::new);
    }

    @Bean
    public Service<Long, Tag, Predicate, Result<List<Tag>>> tagService(TagRepo repo){
        return createService("tag", repo, repo, Tag::new, TagEntity::new);
    }

    @Bean
    public Service<Long, Product, Predicate, Result<List<Product>>> productService(ProductRepo repo){
        return createService("product", repo, repo, Product::new, ProductEntity::new);
    }

    @Bean
    public Service<Long, Payment, Predicate, Result<List<Payment>>> paymentService(PaymentRepo repo){
        return createService("payment", repo, repo, Payment::new, PaymentEntity::new);
    }

    private <D extends Domain<Long>, E extends Entity<Long>> Service<Long, D, Predicate, Result<List<D>>> createService(
            String executorId,
            JpaRepository<E, Long> jpaRepository,
            QuerydslPredicateExecutor<E> querydslPredicateExecutor,
            Function<E, D> toDomainConverter,
            Function<D, E> toEntityConverter
    ){
        return new ServiceBuider<Long, D, Predicate, Result<List<D>>>(new FromAspectConverter<>())
                .savingAspectBuider()
                .executor(new SavingExecutorImpl<D, E>(
                        executorId,
                        jpaRepository,
                        toDomainConverter,
                        toEntityConverter
                ))
                .and()
                .loadingAspectBuilder()
                .executorAll(new CompletelyLoadingExecutorImpl<D, E>(
                        executorId,
                        jpaRepository,
                        toDomainConverter
                ))
                .executorById(new ByIdLoadingExecutorImpl<D, E>(
                        executorId,
                        jpaRepository,
                        toDomainConverter
                ))
                .and()
                .deletingAspectBuilder()
                .executorAll(new CompletelyDeletingExecutorImpl<D, E>(
                        executorId,
                        jpaRepository
                ))
                .executorById(new ByIdDeletingExecutorImpl<D, E>(
                        executorId,
                        jpaRepository
                ))
                .and()
                .predicateAspectBuidler()
                .executor(new PredicateExecutorImpl<D, E>(
                        executorId,
                        querydslPredicateExecutor,
                        toDomainConverter
                ))
                .and()
                .build();
    }
}
