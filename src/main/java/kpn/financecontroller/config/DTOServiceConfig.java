package kpn.financecontroller.config;

import com.querydsl.core.types.Predicate;
import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.data.domains.seller.Seller;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.address.AddressEntity;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.entities.payment.PaymentEntity;
import kpn.financecontroller.data.entities.seller.SellerEntity;
import kpn.financecontroller.data.entities.product.ProductEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.data.services.dto.DTOServiceImpl;
import kpn.financecontroller.data.services.dto.deleters.Deleter;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.services.dto.executors.PredicateExecutor;
import kpn.financecontroller.data.services.dto.loaders.Loader;
import kpn.financecontroller.data.services.dto.DTOService;
import kpn.financecontroller.data.services.dto.savers.Saver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DTOServiceConfig {

    @Bean
    public DTOService<Country, CountryEntity> countryService(Saver<Country, CountryEntity, Long> saver,
                                                             Loader<Country, CountryEntity, Long> loader,
                                                             Deleter<Country, CountryEntity, Long> deleter,
                                                             PredicateExecutor<Country, Predicate> executor){
        return new DTOServiceImpl<>(saver, loader, deleter, executor);
    }

    @Bean
    public DTOService<Region, RegionEntity> regionService(Saver<Region, RegionEntity, Long> saver,
                                                          Loader<Region, RegionEntity, Long> loader,
                                                          Deleter<Region, RegionEntity, Long> deleter,
                                                          PredicateExecutor<Region, Predicate> executor){
        return new DTOServiceImpl<>(saver, loader, deleter, executor);
    }

    @Bean
    public DTOService<City, CityEntity> cityService(Saver<City, CityEntity, Long> saver,
                                                                         Loader<City, CityEntity, Long> loader,
                                                                         Deleter<City, CityEntity, Long> deleter,
                                                                         PredicateExecutor<City, Predicate> executor){
        return new DTOServiceImpl<>(saver, loader, deleter, executor);
    }

    @Bean
    public DTOService<Street, StreetEntity> streetService(Saver<Street, StreetEntity, Long> saver,
                                                                               Loader<Street, StreetEntity, Long> loader,
                                                                               Deleter<Street, StreetEntity, Long> deleter,
                                                                               PredicateExecutor<Street, Predicate> executor){
        return new DTOServiceImpl<>(saver, loader, deleter, executor);
    }

    @Bean
    public DTOService<Address, AddressEntity> addressService(Saver<Address, AddressEntity, Long> saver,
                                                                                  Loader<Address, AddressEntity, Long> loader,
                                                                                  Deleter<Address, AddressEntity, Long> deleter,
                                                                                  PredicateExecutor<Address, Predicate> executor){
        return new DTOServiceImpl<>(saver, loader, deleter, executor);
    }

    @Bean
    public DTOService<Product, ProductEntity> productService(Saver<Product, ProductEntity, Long> saver,
                                                                                  Loader<Product, ProductEntity, Long> loader,
                                                                                  Deleter<Product, ProductEntity, Long> deleter,
                                                                                  PredicateExecutor<Product, Predicate> executor){
        return new DTOServiceImpl<>(saver, loader, deleter, executor);
    }

    @Bean
    public DTOService<Payment, PaymentEntity> paymentService(Saver<Payment, PaymentEntity, Long> saver,
                                                                                  Loader<Payment, PaymentEntity, Long> loader,
                                                                                  Deleter<Payment, PaymentEntity, Long> deleter,
                                                                                  PredicateExecutor<Payment, Predicate> executor){
        return new DTOServiceImpl<>(saver, loader, deleter, executor);
    }

    @Bean
    public DTOService<Tag, TagEntity> tagService(Saver<Tag, TagEntity, Long> saver,
                                                                      Loader<Tag, TagEntity, Long> loader,
                                                                      Deleter<Tag, TagEntity, Long> deleter,
                                                                      PredicateExecutor<Tag, Predicate> executor){
        return new DTOServiceImpl<>(saver, loader, deleter, executor);
    }

    @Bean
    public DTOService<Seller, SellerEntity> sellerService(Saver<Seller, SellerEntity, Long> saver,
                                                                               Loader<Seller, SellerEntity, Long> loader,
                                                                               Deleter<Seller, SellerEntity, Long> deleter,
                                                                               PredicateExecutor<Seller, Predicate> executor){
        return new DTOServiceImpl<>(saver, loader, deleter, executor);
    }
}
