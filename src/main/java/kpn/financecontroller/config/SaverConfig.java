package kpn.financecontroller.config;

// TODO: 17.07.2022 del
//import kpn.financecontroller.data.domains.address.Address;
//import kpn.financecontroller.data.domains.city.City;
//import kpn.financecontroller.data.domains.country.Country;
//import kpn.financecontroller.data.domains.payment.Payment;
//import kpn.financecontroller.data.domains.seller.Seller;
//import kpn.financecontroller.data.domains.product.Product;
//import kpn.financecontroller.data.domains.region.Region;
//import kpn.financecontroller.data.domains.street.Street;
//import kpn.financecontroller.data.domains.tag.Tag;
//import kpn.financecontroller.data.entities.address.AddressEntity;
//import kpn.financecontroller.data.entities.city.CityEntity;
//import kpn.financecontroller.data.entities.country.CountryEntity;
//import kpn.financecontroller.data.entities.payment.PaymentEntity;
//import kpn.financecontroller.data.entities.seller.SellerEntity;
//import kpn.financecontroller.data.entities.product.ProductEntity;
//import kpn.financecontroller.data.entities.region.RegionEntity;
//import kpn.financecontroller.data.entities.street.StreetEntity;
//import kpn.financecontroller.data.entities.tag.TagEntity;
//import kpn.financecontroller.data.services.dto.savers.SaverOld;
//import kpn.financecontroller.data.services.dto.savers.SaverOldImpl;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//@Configuration
//public class SaverConfig {
//
//    @Bean
//    public SaverOld<Country, CountryEntity, Long> countySaver(JpaRepository<CountryEntity, Long> repo){
//        return new SaverOldImpl<>(repo, Country::new, "country");
//    }
//
//    @Bean
//    public SaverOld<Region, RegionEntity, Long> regionSaver(JpaRepository<RegionEntity, Long> repo){
//        return new SaverOldImpl<>(repo, Region::new, "region");
//    }
//
//    @Bean
//    public SaverOld<City, CityEntity, Long> citySaver(JpaRepository<CityEntity, Long> repo){
//        return new SaverOldImpl<>(repo, City::new, "city");
//    }
//
//    @Bean
//    public SaverOld<Street, StreetEntity, Long> streetSaver(JpaRepository<StreetEntity, Long> repo){
//        return new SaverOldImpl<>(repo, Street::new, "street");
//    }
//
//    @Bean
//    public SaverOld<Address, AddressEntity, Long> addressSaver(JpaRepository<AddressEntity, Long> repo){
//        return new SaverOldImpl<>(repo, Address::new, "address");
//    }
//
//    @Bean
//    public SaverOld<Product, ProductEntity, Long> productSaver(JpaRepository<ProductEntity, Long> repo){
//        return new SaverOldImpl<>(repo, Product::new, "product");
//    }
//
//    @Bean
//    public SaverOld<Payment, PaymentEntity, Long> paymentSaver(JpaRepository<PaymentEntity, Long> repo){
//        return new SaverOldImpl<>(repo, Payment::new, "payment");
//    }
//
//    @Bean
//    public SaverOld<Tag, TagEntity, Long> tagSaver(JpaRepository<TagEntity, Long> repo){
//        return new SaverOldImpl<>(repo, Tag::new, "tag");
//    }
//
//    @Bean
//    public SaverOld<Seller, SellerEntity, Long> sellerSaver(JpaRepository<SellerEntity, Long> repo){
//        return new SaverOldImpl<>(repo, Seller::new, "seller");
//    }
//}
