package kpn.financecontroller.config;

import kpn.financecontroller.data.services.deleters.Deleter;
import kpn.financecontroller.data.domains.country.Country;
import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.services.loaders.Loader;
import kpn.financecontroller.data.services.savers.Saver;
import kpn.financecontroller.data.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DTOServiceConfig {

    @Bean
    DTOService<Country, CountryEntity, Long> countryService(Saver<Country, CountryEntity> saver,
                                                            Loader<Country, CountryEntity, Long> loader,
                                                            Deleter<Country, CountryEntity, Long> deleter){
        return new DTOServiceImpl<>(saver, loader, deleter);
    }
}
