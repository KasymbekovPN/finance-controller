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
import kpn.financecontroller.data.services.savers.Saver;
import kpn.financecontroller.data.services.savers.SaverImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

@Configuration
public class SaverConfig {

    @Bean
    public Saver<Country, CountryEntity, Long> countySaver(JpaRepository<CountryEntity, Long> repo){
        return new SaverImpl<>(repo, Country::new, "country");
    }

    @Bean
    public Saver<Region, RegionEntity, Long> regionSaver(JpaRepository<RegionEntity, Long> repo){
        return new SaverImpl<>(repo, Region::new, "region");
    }

    @Bean
    public Saver<City, CityEntity, Long> citySaver(JpaRepository<CityEntity, Long> repo){
        return new SaverImpl<>(repo, City::new, "city");
    }

    @Bean
    public Saver<Street, StreetEntity, Long> streetSaver(JpaRepository<StreetEntity, Long> repo){
        return new SaverImpl<>(repo, Street::new, "street");
    }

    @Bean
    public Saver<Address, AddressEntity, Long> addressSaver(JpaRepository<AddressEntity, Long> repo){
        return new SaverImpl<>(repo, Address::new, "address");
    }

    @Bean
    public Saver<Product, ProductEntity, Long> productSaver(JpaRepository<ProductEntity, Long> repo){
        return new SaverImpl<>(repo, Product::new, "product");
    }

    @Bean
    public Saver<Payment, PaymentEntity, Long> paymentSaver(JpaRepository<PaymentEntity, Long> repo){
        return new SaverImpl<>(repo, Payment::new, "payment");
    }

    @Bean
    public Saver<Tag, TagEntity, Long> tagSaver(JpaRepository<TagEntity, Long> repo){
        return new SaverImpl<>(repo, Tag::new, "tag");
    }

    @Bean
    public Saver<Seller, SellerEntity, Long> sellerSaver(JpaRepository<SellerEntity, Long> repo){
        return new SaverImpl<>(repo, Seller::new, "seller");
    }
}
