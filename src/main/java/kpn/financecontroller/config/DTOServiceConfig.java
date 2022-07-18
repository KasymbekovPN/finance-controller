package kpn.financecontroller.config;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.converters.aspect.FromAspectConverter;
import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.domains.seller.Seller;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.entities.address.AddressEntity;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.entities.seller.SellerEntity;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.repos.address.AddressRepo;
import kpn.financecontroller.data.repos.city.CityRepo;
import kpn.financecontroller.data.repos.country.CountryRepo;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.repos.region.RegionRepo;
import kpn.financecontroller.data.repos.seller.SellerRepo;
import kpn.financecontroller.data.repos.street.StreetRepo;
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
