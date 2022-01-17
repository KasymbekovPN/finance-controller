package kpn.financecontroller.config;

import kpn.financecontroller.data.domains.building.Building;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.entities.building.BuildingEntity;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.services.savers.Saver;
import kpn.financecontroller.data.services.savers.SaverImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

@Configuration
public class SaverConfig {

    @Bean
    public Saver<Country, CountryEntity, Long> countySaver(JpaRepository<CountryEntity, Long> repo){
        return new SaverImpl<>(repo, Country::new);
    }

    @Bean
    public Saver<Region, RegionEntity, Long> regionSaver(JpaRepository<RegionEntity, Long> repo){
        return new SaverImpl<>(repo, Region::new);
    }

    @Bean
    public Saver<City, CityEntity, Long> citySaver(JpaRepository<CityEntity, Long> repo){
        return new SaverImpl<>(repo, City::new);
    }

    @Bean
    public Saver<Street, StreetEntity, Long> streetSaver(JpaRepository<StreetEntity, Long> repo){
        return new SaverImpl<>(repo, Street::new);
    }

    @Bean
    public Saver<Building, BuildingEntity, Long> buildingSaver(JpaRepository<BuildingEntity, Long> repo){
        return new SaverImpl<>(repo, Building::new);
    }
}
