package kpn.financecontroller.config;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.converter.aspect.FromAspectConverter;
import kpn.financecontroller.data.domain.Address;
import kpn.financecontroller.data.domain.City;
import kpn.financecontroller.data.domain.Payment;
import kpn.financecontroller.data.domain.Product;
import kpn.financecontroller.data.domain.Region;
import kpn.financecontroller.data.domain.Seller;
import kpn.financecontroller.data.domain.Street;
import kpn.financecontroller.data.domain.Tag;
import kpn.financecontroller.data.entity.AddressEntity;
import kpn.financecontroller.data.entity.CityEntity;
import kpn.financecontroller.data.entity.CountryEntity;
import kpn.financecontroller.data.entity.PaymentEntity;
import kpn.financecontroller.data.entity.ProductEntity;
import kpn.financecontroller.data.entity.RegionEntity;
import kpn.financecontroller.data.entity.SellerEntity;
import kpn.financecontroller.data.entity.StreetEntity;
import kpn.financecontroller.data.entity.TagEntity;
import kpn.financecontroller.data.repo.AddressRepo;
import kpn.financecontroller.data.repo.CityRepo;
import kpn.financecontroller.data.repo.CountryRepo;
import kpn.financecontroller.data.domain.Country;
import kpn.financecontroller.data.repo.PaymentRepo;
import kpn.financecontroller.data.repo.ProductRepo;
import kpn.financecontroller.data.repo.RegionRepo;
import kpn.financecontroller.data.repo.SellerRepo;
import kpn.financecontroller.data.repo.StreetRepo;
import kpn.financecontroller.data.repo.TagRepo;
import kpn.financecontroller.data.services.dto.deleter.ByIdDeletingExecutorImpl;
import kpn.financecontroller.data.services.dto.deleter.CompletelyDeletingExecutorImpl;
import kpn.financecontroller.data.services.dto.executor.PredicateExecutorImpl;
import kpn.financecontroller.data.services.dto.loader.ByIdLoadingExecutorImpl;
import kpn.financecontroller.data.services.dto.loader.CompletelyLoadingExecutorImpl;
import kpn.financecontroller.data.services.dto.saver.SavingExecutorImpl;
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
