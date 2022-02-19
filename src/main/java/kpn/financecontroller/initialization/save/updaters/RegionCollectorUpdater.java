package kpn.financecontroller.initialization.save.updaters;

import kpn.financecontroller.initialization.entities.RegionInitialEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class RegionCollectorUpdater extends AbstractCollectorUpdater<Long, RegionInitialEntity> {}
