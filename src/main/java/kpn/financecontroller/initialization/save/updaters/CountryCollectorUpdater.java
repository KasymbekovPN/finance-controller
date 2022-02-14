package kpn.financecontroller.initialization.save.updaters;

import kpn.financecontroller.initialization.entities.CountryInitialEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class CountryCollectorUpdater extends AbstractCollectorUpdater<Long, CountryInitialEntity> {}
