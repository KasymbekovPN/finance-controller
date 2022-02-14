package kpn.financecontroller.initialization.load.factories;

import kpn.financecontroller.initialization.entities.CountryInitialEntity;
import kpn.financecontroller.initialization.load.calculators.PathCalculator;
import kpn.financecontroller.initialization.load.creators.CollectorCreator;
import kpn.financecontroller.initialization.load.tasks.LoadingTask;
import kpn.financecontroller.initialization.load.tasks.LoadingTaskImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

// TODO: 14.02.2022 add base class
@Service
@Profile("dev")
public class CountryLoadingTaskFactory implements LoadingTaskFactory<Long, CountryInitialEntity> {

    private static final String PATH = "countries.json";
    private static final String ID = "COUNTRIES";

    private final PathCalculator<String, String> pathCalculator;
    private final CollectorCreator<Long, CountryInitialEntity> collectorCreator;

    @Autowired
     public CountryLoadingTaskFactory(PathCalculator<String, String> pathCalculator, CollectorCreator<Long, CountryInitialEntity> collectorCreator) {
          this.pathCalculator = pathCalculator;
          this.collectorCreator = collectorCreator;
     }

     @Override
     public LoadingTask<Long, CountryInitialEntity> create() {
          return new LoadingTaskImpl<>(pathCalculator, collectorCreator, PATH, ID);
     }
}
