package kpn.financecontroller.config;

import kpn.financecontroller.data.domains.building.Building;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.product.Product;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.entities.building.BuildingEntity;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.entities.product.ProductEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.services.deleters.DeleterAllAndById;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

@Configuration
public class DeleterConfig {

    @Bean
    public DeleterAllAndById<Country, CountryEntity, Long> countryDeleter(JpaRepository<CountryEntity, Long> repo){
        return new DeleterAllAndById<>(repo);
    }

    @Bean
    public DeleterAllAndById<Region, RegionEntity, Long> regionDeleter(JpaRepository<RegionEntity, Long> repo){
        return new DeleterAllAndById<>(repo);
    }

    @Bean
    public DeleterAllAndById<City, CityEntity, Long> cityDeleter(JpaRepository<CityEntity, Long> repo){
        return new DeleterAllAndById<>(repo);
    }

    @Bean
    public DeleterAllAndById<Street, StreetEntity, Long> streetDeleter(JpaRepository<StreetEntity, Long> repo){
        return new DeleterAllAndById<>(repo);
    }

    @Bean
    public DeleterAllAndById<Building, BuildingEntity, Long> buildingDeleter(JpaRepository<BuildingEntity, Long> repo){
        return new DeleterAllAndById<>(repo);
    }

    @Bean
    public DeleterAllAndById<Product, ProductEntity, Long> productDeleter(JpaRepository<ProductEntity, Long> repo){
        return new DeleterAllAndById<>(repo);
    }
}
