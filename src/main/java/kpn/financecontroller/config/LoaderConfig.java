package kpn.financecontroller.config;

import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.services.loaders.Loader;
import kpn.financecontroller.data.services.loaders.LoaderAll;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class LoaderConfig {

    @Bean
    public Loader<Country, CountryEntity, Long> countryLoader(JpaRepository<CountryEntity, Long> repo){
        Function<List<CountryEntity>, List<Country>> toDomains = (entities) -> {
            return entities.stream().map(Country::new).collect(Collectors.toList());
        };
        return new LoaderAll<>(repo, Country::new, toDomains);
    }

    @Bean
    public Loader<Region, RegionEntity, Long> regionLoader(JpaRepository<RegionEntity, Long> repo){
        Function<List<RegionEntity>, List<Region>> toDomains = (entities) -> {
            return entities.stream().map(Region::new).collect(Collectors.toList());
        };
        return new LoaderAll<>(repo, Region::new, toDomains);
    }

    @Bean
    public Loader<City, CityEntity, Long> cityLoader(JpaRepository<CityEntity, Long> repo){
        Function<List<CityEntity>, List<City>> toDomains = (entities) -> {
            return entities.stream().map(City::new).collect(Collectors.toList());
        };
        return new LoaderAll<>(repo, City::new, toDomains);
    }

    @Bean
    public Loader<Street, StreetEntity, Long> streetLoader(JpaRepository<StreetEntity, Long> repo){
        Function<List<StreetEntity>, List<Street>> toDomains = (entities) -> {
            return entities.stream().map(Street::new).collect(Collectors.toList());
        };
        return new LoaderAll<>(repo, Street::new, toDomains);
    }
}
