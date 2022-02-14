package kpn.financecontroller.initialization.load.factories;

import kpn.financecontroller.initialization.entities.CountryInitialEntity;
import kpn.financecontroller.initialization.load.calculators.PathCalculator;
import kpn.financecontroller.initialization.load.creators.CollectorCreator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class CountryLoadingTaskFactory extends AbstractLoadingTaskFactory<Long, CountryInitialEntity> {

    private static final String PATH = "countries.json";
    private static final String ID = "COUNTRIES";

    public CountryLoadingTaskFactory(PathCalculator<String, String> pathCalculator,
                                     CollectorCreator<Long, CountryInitialEntity> collectorCreator) {
        super(pathCalculator, collectorCreator, ID, PATH);
    }
}
