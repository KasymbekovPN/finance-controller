package kpn.financecontroller.config;

import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.payment.Payment;
import kpn.financecontroller.data.domains.place.Place;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.domains.tag.Tag;
import kpn.financecontroller.data.entities.address.AddressEntity;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.entities.payment.PaymentEntity;
import kpn.financecontroller.data.entities.place.PlaceEntity;
import kpn.financecontroller.data.entities.product.ProductEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.data.services.deleters.DeleterAllAndById;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

@Configuration
public class DeleterConfig {

    @Bean
    public DeleterAllAndById<Country, CountryEntity, Long> countryDeleter(JpaRepository<CountryEntity, Long> repo){
        return new DeleterAllAndById<>(repo, "country");
    }

    @Bean
    public DeleterAllAndById<Region, RegionEntity, Long> regionDeleter(JpaRepository<RegionEntity, Long> repo){
        return new DeleterAllAndById<>(repo, "region");
    }

    @Bean
    public DeleterAllAndById<City, CityEntity, Long> cityDeleter(JpaRepository<CityEntity, Long> repo){
        return new DeleterAllAndById<>(repo, "city");
    }

    @Bean
    public DeleterAllAndById<Street, StreetEntity, Long> streetDeleter(JpaRepository<StreetEntity, Long> repo){
        return new DeleterAllAndById<>(repo, "street");
    }

    @Bean
    public DeleterAllAndById<Address, AddressEntity, Long> addressDeleter(JpaRepository<AddressEntity, Long> repo){
        return new DeleterAllAndById<>(repo, "builder");
    }

    @Bean
    public DeleterAllAndById<Product, ProductEntity, Long> productDeleter(JpaRepository<ProductEntity, Long> repo){
        return new DeleterAllAndById<>(repo, "product");
    }

    @Bean
    public DeleterAllAndById<Payment, PaymentEntity, Long> paymentDeleter(JpaRepository<PaymentEntity, Long> repo){
        return new DeleterAllAndById<>(repo, "payment");
    }

    @Bean
    public DeleterAllAndById<Tag, TagEntity, Long> tagDeleter(JpaRepository<TagEntity, Long> repo){
        return new DeleterAllAndById<>(repo, "tag");
    }

    @Bean
    public DeleterAllAndById<Place, PlaceEntity, Long> placeDeleter(JpaRepository<PlaceEntity, Long> repo){
        return new DeleterAllAndById<>(repo, "place");
    }
}
