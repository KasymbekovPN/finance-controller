package kpn.financecontroller.config;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.converter.aspect.FromAspectConverter;
import kpn.financecontroller.data.domain.*;
import kpn.financecontroller.data.entity.*;
import kpn.financecontroller.data.repo.*;
import kpn.financecontroller.data.services.dto.deleter.ByIdDeletingExecutorImpl;
import kpn.financecontroller.data.services.dto.deleter.CompletelyDeletingExecutorImpl;
import kpn.financecontroller.data.services.dto.executor.PredicateExecutorImpl;
import kpn.financecontroller.data.services.dto.loader.ByIdLoadingExecutorImpl;
import kpn.financecontroller.data.services.dto.loader.CompletelyLoadingExecutorImpl;
import kpn.financecontroller.data.services.dto.saver.SavingExecutorImpl;
import kpn.financecontroller.data.services.dto.service.*;
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
        return new CountryDtoDecorator(createService("country", repo, repo, Country::new, CountryEntity::new));
    }

    @Bean
    public Service<Long, Region, Predicate, Result<List<Region>>> regionService(RegionRepo repo){
        return new RegionDtoDecorator(createService("region", repo, repo, Region::new, RegionEntity::new));
    }

    @Bean
    public Service<Long, City, Predicate, Result<List<City>>> cityService(CityRepo repo){
        return new CityDtoDecorator(createService("city", repo, repo, City::new, CityEntity::new));
    }

    @Bean
    public Service<Long, Street, Predicate, Result<List<Street>>> streetService(StreetRepo repo){
        return new StreetDtoDecorator(createService("street", repo, repo, Street::new, StreetEntity::new));
    }

    @Bean
    public Service<Long, Address, Predicate, Result<List<Address>>> addressService(AddressRepo repo){
        return new AddressDtoDecorator(createService("address", repo, repo, Address::new, AddressEntity::new));
    }

    @Bean
    public Service<Long, Seller, Predicate, Result<List<Seller>>> sellerService(SellerRepo repo){
        return new SellerDtoDecorator(createService("seller", repo, repo, Seller::new, SellerEntity::new));
    }

    @Bean
    public Service<Long, Tag, Predicate, Result<List<Tag>>> tagService(TagRepo repo){
        return new TagDtoDecorator(createService("tag", repo, repo, Tag::new, TagEntity::new));
    }

    @Bean
    public Service<Long, Product, Predicate, Result<List<Product>>> productService(ProductRepo repo){
        return new ProductDtoDecorator(createService("product", repo, repo, Product::new, ProductEntity::new));
    }

    @Bean
    public Service<Long, Payment, Predicate, Result<List<Payment>>> paymentService(PaymentRepo repo){
        return new PaymentDtoDecorator(createService("payment", repo, repo, Payment::new, PaymentEntity::new));
    }

    @Bean
    public Service<Long, Action, Predicate, Result<List<Action>>> actionService(ActionRepo repo){
        return new ActionDtoDecorator(createService("action", repo, repo, Action::new, ActionEntity::new));
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
