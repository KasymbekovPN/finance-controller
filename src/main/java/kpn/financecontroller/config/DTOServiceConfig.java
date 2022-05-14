package kpn.financecontroller.config;

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
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.data.services.DTOServiceImpl;
import kpn.financecontroller.data.services.deleters.Deleter;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.services.loaders.Loader;
import kpn.financecontroller.data.services.savers.Saver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DTOServiceConfig {

    @Bean
    public DTOService<Country, CountryEntity, Long> countryService(Saver<Country, CountryEntity, Long> saver,
                                                                   Loader<Country, CountryEntity, Long> loader,
                                                                   Deleter<Country, CountryEntity, Long> deleter){
        return new DTOServiceImpl<>(saver, loader, deleter);
    }

    @Bean
    public DTOService<Region, RegionEntity, Long> regionService(Saver<Region, RegionEntity, Long> saver,
                                                                Loader<Region, RegionEntity, Long> loader,
                                                                Deleter<Region, RegionEntity, Long> deleter){
        return new DTOServiceImpl<>(saver, loader, deleter);
    }

    @Bean
    public DTOService<City, CityEntity, Long> cityService(Saver<City, CityEntity, Long> saver,
                                                            Loader<City, CityEntity, Long> loader,
                                                            Deleter<City, CityEntity, Long> deleter){
        return new DTOServiceImpl<>(saver, loader, deleter);
    }

    @Bean
    public DTOService<Street, StreetEntity, Long> streetService(Saver<Street, StreetEntity, Long> saver,
                                                              Loader<Street, StreetEntity, Long> loader,
                                                              Deleter<Street, StreetEntity, Long> deleter){
        return new DTOServiceImpl<>(saver, loader, deleter);
    }

    @Bean
    public DTOService<Address, AddressEntity, Long> addressService(Saver<Address, AddressEntity, Long> saver,
                                                                   Loader<Address, AddressEntity, Long> loader,
                                                                   Deleter<Address, AddressEntity, Long> deleter){
        return new DTOServiceImpl<>(saver, loader, deleter);
    }

    @Bean
    public DTOService<Product, ProductEntity, Long> productService(Saver<Product, ProductEntity, Long> saver,
                                                                    Loader<Product, ProductEntity, Long> loader,
                                                                    Deleter<Product, ProductEntity, Long> deleter){
        return new DTOServiceImpl<>(saver, loader, deleter);
    }

    @Bean
    public DTOService<Payment, PaymentEntity, Long> paymentService(Saver<Payment, PaymentEntity, Long> saver,
                                                                   Loader<Payment, PaymentEntity, Long> loader,
                                                                   Deleter<Payment, PaymentEntity, Long> deleter){
        return new DTOServiceImpl<>(saver, loader, deleter);
    }

    @Bean
    public DTOService<Tag, TagEntity, Long> tagService(Saver<Tag, TagEntity, Long> saver,
                                                           Loader<Tag, TagEntity, Long> loader,
                                                           Deleter<Tag, TagEntity, Long> deleter){
        return new DTOServiceImpl<>(saver, loader, deleter);
    }

    @Bean
    public DTOService<Seller, SellerEntity, Long> sellerService(Saver<Seller, SellerEntity, Long> saver,
                                                                Loader<Seller, SellerEntity, Long> loader,
                                                                Deleter<Seller, SellerEntity, Long> deleter){
        return new DTOServiceImpl<>(saver, loader, deleter);
    }
}
