package kpn.financecontroller.config;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.converters.aspect.FromAspectConverter;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.repos.country.CountryRepo;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.services.dto.deleters.ByIdDeletingExecutorImpl;
import kpn.financecontroller.data.services.dto.deleters.CompletelyDeletingExecutorImpl;
import kpn.financecontroller.data.services.dto.executors.PredicateExecutorImpl;
import kpn.financecontroller.data.services.dto.loaders.ByIdLoadingExecutorImpl;
import kpn.financecontroller.data.services.dto.loaders.CompletelyLoadingExecutorImpl;
import kpn.financecontroller.data.services.dto.savers.SavingExecutorImpl;
import kpn.lib.aspect.AspectResult;
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

    // TODO: 17.07.2022 del
//    @Bean
//    public DTOServiceOLdOld<Country, CountryEntity> countryService(SaverOld<Country, CountryEntity, Long> saverOld,
//                                                                   LoaderOld<Country, CountryEntity, Long> loaderOld,
//                                                                   DeleterOld<Country, CountryEntity, Long> deleterOld,
//                                                                   PredicateExecutorOld<Country, Predicate> executor){
//        return new DTOServiceOLdImplOld<>(saverOld, loaderOld, deleterOld, executor);
//    }
//
//    @Bean
//    public DTOServiceOLdOld<Region, RegionEntity> regionService(SaverOld<Region, RegionEntity, Long> saverOld,
//                                                                LoaderOld<Region, RegionEntity, Long> loaderOld,
//                                                                DeleterOld<Region, RegionEntity, Long> deleterOld,
//                                                                PredicateExecutorOld<Region, Predicate> executor){
//        return new DTOServiceOLdImplOld<>(saverOld, loaderOld, deleterOld, executor);
//    }
//
//    @Bean
//    public DTOServiceOLdOld<City, CityEntity> cityService(SaverOld<City, CityEntity, Long> saverOld,
//                                                          LoaderOld<City, CityEntity, Long> loaderOld,
//                                                          DeleterOld<City, CityEntity, Long> deleterOld,
//                                                          PredicateExecutorOld<City, Predicate> executor){
//        return new DTOServiceOLdImplOld<>(saverOld, loaderOld, deleterOld, executor);
//    }
//
//    @Bean
//    public DTOServiceOLdOld<Street, StreetEntity> streetService(SaverOld<Street, StreetEntity, Long> saverOld,
//                                                                LoaderOld<Street, StreetEntity, Long> loaderOld,
//                                                                DeleterOld<Street, StreetEntity, Long> deleterOld,
//                                                                PredicateExecutorOld<Street, Predicate> executor){
//        return new DTOServiceOLdImplOld<>(saverOld, loaderOld, deleterOld, executor);
//    }
//
//    @Bean
//    public DTOServiceOLdOld<Address, AddressEntity> addressService(SaverOld<Address, AddressEntity, Long> saverOld,
//                                                                   LoaderOld<Address, AddressEntity, Long> loaderOld,
//                                                                   DeleterOld<Address, AddressEntity, Long> deleterOld,
//                                                                   PredicateExecutorOld<Address, Predicate> executor){
//        return new DTOServiceOLdImplOld<>(saverOld, loaderOld, deleterOld, executor);
//    }
//
//    @Bean
//    public DTOServiceOLdOld<Product, ProductEntity> productService(SaverOld<Product, ProductEntity, Long> saverOld,
//                                                                   LoaderOld<Product, ProductEntity, Long> loaderOld,
//                                                                   DeleterOld<Product, ProductEntity, Long> deleterOld,
//                                                                   PredicateExecutorOld<Product, Predicate> executor){
//        return new DTOServiceOLdImplOld<>(saverOld, loaderOld, deleterOld, executor);
//    }
//
//    @Bean
//    public DTOServiceOLdOld<Payment, PaymentEntity> paymentService(SaverOld<Payment, PaymentEntity, Long> saverOld,
//                                                                   LoaderOld<Payment, PaymentEntity, Long> loaderOld,
//                                                                   DeleterOld<Payment, PaymentEntity, Long> deleterOld,
//                                                                   PredicateExecutorOld<Payment, Predicate> executor){
//        return new DTOServiceOLdImplOld<>(saverOld, loaderOld, deleterOld, executor);
//    }
//
//    @Bean
//    public DTOServiceOLdOld<Tag, TagEntity> tagService(SaverOld<Tag, TagEntity, Long> saverOld,
//                                                       LoaderOld<Tag, TagEntity, Long> loaderOld,
//                                                       DeleterOld<Tag, TagEntity, Long> deleterOld,
//                                                       PredicateExecutorOld<Tag, Predicate> executor){
//        return new DTOServiceOLdImplOld<>(saverOld, loaderOld, deleterOld, executor);
//    }
//
//    @Bean
//    public DTOServiceOLdOld<Seller, SellerEntity> sellerService(SaverOld<Seller, SellerEntity, Long> saverOld,
//                                                                LoaderOld<Seller, SellerEntity, Long> loaderOld,
//                                                                DeleterOld<Seller, SellerEntity, Long> deleterOld,
//                                                                PredicateExecutorOld<Seller, Predicate> executor){
//        return new DTOServiceOLdImplOld<>(saverOld, loaderOld, deleterOld, executor);
//    }
}
