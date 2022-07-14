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
import kpn.financecontroller.data.services.dto.DTOServiceOLdImplOld;
import kpn.financecontroller.data.services.dto.deleters.DeleterOld;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.services.dto.executors.PredicateExecutorOld;
import kpn.financecontroller.data.services.dto.loaders.LoaderOld;
import kpn.financecontroller.data.services.dto.DTOServiceOLdOld;
import kpn.financecontroller.data.services.dto.savers.SaverOld;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DTOServiceConfig {

    @Bean
    public DTOServiceOLdOld<Country, CountryEntity> countryService(SaverOld<Country, CountryEntity, Long> saverOld,
                                                                   LoaderOld<Country, CountryEntity, Long> loaderOld,
                                                                   DeleterOld<Country, CountryEntity, Long> deleterOld,
                                                                   PredicateExecutorOld<Country, Predicate> executor){
        return new DTOServiceOLdImplOld<>(saverOld, loaderOld, deleterOld, executor);
    }

    @Bean
    public DTOServiceOLdOld<Region, RegionEntity> regionService(SaverOld<Region, RegionEntity, Long> saverOld,
                                                                LoaderOld<Region, RegionEntity, Long> loaderOld,
                                                                DeleterOld<Region, RegionEntity, Long> deleterOld,
                                                                PredicateExecutorOld<Region, Predicate> executor){
        return new DTOServiceOLdImplOld<>(saverOld, loaderOld, deleterOld, executor);
    }

    @Bean
    public DTOServiceOLdOld<City, CityEntity> cityService(SaverOld<City, CityEntity, Long> saverOld,
                                                          LoaderOld<City, CityEntity, Long> loaderOld,
                                                          DeleterOld<City, CityEntity, Long> deleterOld,
                                                          PredicateExecutorOld<City, Predicate> executor){
        return new DTOServiceOLdImplOld<>(saverOld, loaderOld, deleterOld, executor);
    }

    @Bean
    public DTOServiceOLdOld<Street, StreetEntity> streetService(SaverOld<Street, StreetEntity, Long> saverOld,
                                                                LoaderOld<Street, StreetEntity, Long> loaderOld,
                                                                DeleterOld<Street, StreetEntity, Long> deleterOld,
                                                                PredicateExecutorOld<Street, Predicate> executor){
        return new DTOServiceOLdImplOld<>(saverOld, loaderOld, deleterOld, executor);
    }

    @Bean
    public DTOServiceOLdOld<Address, AddressEntity> addressService(SaverOld<Address, AddressEntity, Long> saverOld,
                                                                   LoaderOld<Address, AddressEntity, Long> loaderOld,
                                                                   DeleterOld<Address, AddressEntity, Long> deleterOld,
                                                                   PredicateExecutorOld<Address, Predicate> executor){
        return new DTOServiceOLdImplOld<>(saverOld, loaderOld, deleterOld, executor);
    }

    @Bean
    public DTOServiceOLdOld<Product, ProductEntity> productService(SaverOld<Product, ProductEntity, Long> saverOld,
                                                                   LoaderOld<Product, ProductEntity, Long> loaderOld,
                                                                   DeleterOld<Product, ProductEntity, Long> deleterOld,
                                                                   PredicateExecutorOld<Product, Predicate> executor){
        return new DTOServiceOLdImplOld<>(saverOld, loaderOld, deleterOld, executor);
    }

    @Bean
    public DTOServiceOLdOld<Payment, PaymentEntity> paymentService(SaverOld<Payment, PaymentEntity, Long> saverOld,
                                                                   LoaderOld<Payment, PaymentEntity, Long> loaderOld,
                                                                   DeleterOld<Payment, PaymentEntity, Long> deleterOld,
                                                                   PredicateExecutorOld<Payment, Predicate> executor){
        return new DTOServiceOLdImplOld<>(saverOld, loaderOld, deleterOld, executor);
    }

    @Bean
    public DTOServiceOLdOld<Tag, TagEntity> tagService(SaverOld<Tag, TagEntity, Long> saverOld,
                                                       LoaderOld<Tag, TagEntity, Long> loaderOld,
                                                       DeleterOld<Tag, TagEntity, Long> deleterOld,
                                                       PredicateExecutorOld<Tag, Predicate> executor){
        return new DTOServiceOLdImplOld<>(saverOld, loaderOld, deleterOld, executor);
    }

    @Bean
    public DTOServiceOLdOld<Seller, SellerEntity> sellerService(SaverOld<Seller, SellerEntity, Long> saverOld,
                                                                LoaderOld<Seller, SellerEntity, Long> loaderOld,
                                                                DeleterOld<Seller, SellerEntity, Long> deleterOld,
                                                                PredicateExecutorOld<Seller, Predicate> executor){
        return new DTOServiceOLdImplOld<>(saverOld, loaderOld, deleterOld, executor);
    }
}
