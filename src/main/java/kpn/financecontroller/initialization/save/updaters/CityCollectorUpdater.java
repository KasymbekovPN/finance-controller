package kpn.financecontroller.initialization.save.updaters;

import kpn.financecontroller.initialization.entities.CityInitialEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class CityCollectorUpdater extends AbstractCollectorUpdater<Long, CityInitialEntity> {}
