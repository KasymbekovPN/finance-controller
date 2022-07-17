package kpn.financecontroller.config;

// TODO: 17.07.2022 del
//import kpn.financecontroller.data.converters.entity2domain.ConstructEntitiesToDomainsConverter;
//import kpn.financecontroller.data.domains.address.Address;
//import kpn.financecontroller.data.domains.city.City;
//import kpn.financecontroller.data.domains.country.Country;
//import kpn.financecontroller.data.domains.payment.Payment;
//import kpn.financecontroller.data.domains.product.Product;
//import kpn.financecontroller.data.domains.region.Region;
//import kpn.financecontroller.data.domains.seller.Seller;
//import kpn.financecontroller.data.domains.street.Street;
//import kpn.financecontroller.data.domains.tag.Tag;
//import kpn.financecontroller.data.entities.address.AddressEntity;
//import kpn.financecontroller.data.entities.city.CityEntity;
//import kpn.financecontroller.data.entities.country.CountryEntity;
//import kpn.financecontroller.data.entities.payment.PaymentEntity;
//import kpn.financecontroller.data.entities.product.ProductEntity;
//import kpn.financecontroller.data.entities.region.RegionEntity;
//import kpn.financecontroller.data.entities.seller.SellerEntity;
//import kpn.financecontroller.data.entities.street.StreetEntity;
//import kpn.financecontroller.data.entities.tag.TagEntity;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//import java.util.function.Function;
//
//@Configuration
//public class EntityToDomainConfig {
//
//    // TODO: 13.07.2022 ???
////    @Bean
////    public Function<List<CountryEntity>, List<Country>> countryEntitiesToDomainsConverter(){
////        return new ConstructEntitiesToDomainsConverter<CountryEntity, Country>(Country::new);
////    }
//
//    @Bean
//    public Function<List<RegionEntity>, List<Region>> regionEntitiesToDomainsConverter(){
//        return new ConstructEntitiesToDomainsConverter<RegionEntity, Region>(Region::new);
//    }
//
//    @Bean
//    public Function<List<CityEntity>, List<City>> cityEntitiesToDomainsConverter(){
//        return new ConstructEntitiesToDomainsConverter<CityEntity, City>(City::new);
//    }
//
//    @Bean
//    public Function<List<StreetEntity>, List<Street>> streetEntitiesToDomainsConverter(){
//        return new ConstructEntitiesToDomainsConverter<StreetEntity, Street>(Street::new);
//    }
//
//    @Bean
//    public Function<List<AddressEntity>, List<Address>> addressEntitiesToDomainsConverter(){
//        return new ConstructEntitiesToDomainsConverter<AddressEntity, Address>(Address::new);
//    }
//
//    @Bean
//    public Function<List<SellerEntity>, List<Seller>> sellerEntitiesToDomainsConverter(){
//        return new ConstructEntitiesToDomainsConverter<SellerEntity, Seller>(Seller::new);
//    }
//
//    @Bean
//    public Function<List<PaymentEntity>, List<Payment>> paymentEntitiesToDomainsConverter(){
//        return new ConstructEntitiesToDomainsConverter<PaymentEntity, Payment>(Payment::new);
//    }
//
//    @Bean
//    public Function<List<TagEntity>, List<Tag>> tagEntitiesToDomainsConverter(){
//        return new ConstructEntitiesToDomainsConverter<TagEntity, Tag>(Tag::new);
//    }
//
//    @Bean
//    public Function<List<ProductEntity>, List<Product>> productEntitiesToDomainsConverter(){
//        return new ConstructEntitiesToDomainsConverter<ProductEntity, Product>(Product::new);
//    }
//}
