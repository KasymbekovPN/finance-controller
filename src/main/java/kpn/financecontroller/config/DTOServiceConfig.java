package kpn.financecontroller.config;

import kpn.financecontroller.data.domains.building.Building;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.entities.building.BuildingEntity;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.services.deleters.Deleter;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.services.loaders.Loader;
import kpn.financecontroller.data.services.savers.Saver;
import kpn.financecontroller.data.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// TODO: 17.01.2022 construct it through BPP
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
    public DTOService<Building, BuildingEntity, Long> buildingService(Saver<Building, BuildingEntity, Long> saver,
                                                                     Loader<Building, BuildingEntity, Long> loader,
                                                                     Deleter<Building, BuildingEntity, Long> deleter){
        return new DTOServiceImpl<>(saver, loader, deleter);
    }
}
