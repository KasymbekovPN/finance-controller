package kpn.financecontroller.config;

import kpn.financecontroller.data.deleters.Deleter;
import kpn.financecontroller.data.domains.currency.Currency;
import kpn.financecontroller.data.entities.currency.CurrencyEntity;
import kpn.financecontroller.data.loaders.Loader;
import kpn.financecontroller.data.savers.Saver;
import kpn.financecontroller.data.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DTOServiceConfig {

    @Bean
    public DTOService<Currency, CurrencyEntity, Long> currencyService(Saver<Currency, CurrencyEntity> saver,
                                                                      Loader<Currency, CurrencyEntity, Long> loader,
                                                                      Deleter<Currency, CurrencyEntity, Long> deleter){
        return new DTOServiceImpl<Currency, CurrencyEntity, Long>(saver, loader, deleter);
    }
}
