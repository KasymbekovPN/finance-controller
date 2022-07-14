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
import kpn.financecontroller.data.services.dto.deleters.DeleterOldAllAndById;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

@Configuration
public class DeleterConfig {

    @Bean
    public DeleterOldAllAndById<Country, CountryEntity, Long> countryDeleter(JpaRepository<CountryEntity, Long> repo){
        return new DeleterOldAllAndById<>(repo, "country");
    }

    @Bean
    public DeleterOldAllAndById<Region, RegionEntity, Long> regionDeleter(JpaRepository<RegionEntity, Long> repo){
        return new DeleterOldAllAndById<>(repo, "region");
    }

    @Bean
    public DeleterOldAllAndById<City, CityEntity, Long> cityDeleter(JpaRepository<CityEntity, Long> repo){
        return new DeleterOldAllAndById<>(repo, "city");
    }

    @Bean
    public DeleterOldAllAndById<Street, StreetEntity, Long> streetDeleter(JpaRepository<StreetEntity, Long> repo){
        return new DeleterOldAllAndById<>(repo, "street");
    }

    @Bean
    public DeleterOldAllAndById<Address, AddressEntity, Long> addressDeleter(JpaRepository<AddressEntity, Long> repo){
        return new DeleterOldAllAndById<>(repo, "builder");
    }

    @Bean
    public DeleterOldAllAndById<Product, ProductEntity, Long> productDeleter(JpaRepository<ProductEntity, Long> repo){
        return new DeleterOldAllAndById<>(repo, "product");
    }

    @Bean
    public DeleterOldAllAndById<Payment, PaymentEntity, Long> paymentDeleter(JpaRepository<PaymentEntity, Long> repo){
        return new DeleterOldAllAndById<>(repo, "payment");
    }

    @Bean
    public DeleterOldAllAndById<Tag, TagEntity, Long> tagDeleter(JpaRepository<TagEntity, Long> repo){
        return new DeleterOldAllAndById<>(repo, "tag");
    }

    @Bean
    public DeleterOldAllAndById<Seller, SellerEntity, Long> sellerDeleter(JpaRepository<SellerEntity, Long> repo){
        return new DeleterOldAllAndById<>(repo, "seller");
    }
}
