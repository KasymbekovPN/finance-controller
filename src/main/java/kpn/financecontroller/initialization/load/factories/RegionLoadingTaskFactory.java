package kpn.financecontroller.initialization.load.factories;

import kpn.financecontroller.initialization.entities.RegionInitialEntity;
import kpn.financecontroller.initialization.load.calculators.PathCalculator;
import kpn.financecontroller.initialization.load.creators.CollectorCreator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class RegionLoadingTaskFactory extends AbstractLoadingTaskFactory<Long, RegionInitialEntity> {

    private static final String PATH = "regions.json";
    private static final String ID = "REGIONS";

    public RegionLoadingTaskFactory(PathCalculator<String, String> pathCalculator,
                                    CollectorCreator<Long, RegionInitialEntity> collectorCreator) {
        super(pathCalculator, collectorCreator, ID, PATH);
    }
}
