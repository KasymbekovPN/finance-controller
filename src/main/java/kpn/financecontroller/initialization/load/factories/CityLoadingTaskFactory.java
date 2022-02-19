package kpn.financecontroller.initialization.load.factories;

import kpn.financecontroller.initialization.entities.CityInitialEntity;
import kpn.financecontroller.initialization.load.calculators.PathCalculator;
import kpn.financecontroller.initialization.load.creators.CollectorCreator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class CityLoadingTaskFactory extends AbstractLoadingTaskFactory<Long, CityInitialEntity> {

    private static final String PATH = "cities.json";
    private static final String ID = "CITIES";

    public CityLoadingTaskFactory(PathCalculator<String, String> pathCalculator,
                                  CollectorCreator<Long, CityInitialEntity> collectorCreator) {
        super(pathCalculator, collectorCreator, ID, PATH);
    }
}
