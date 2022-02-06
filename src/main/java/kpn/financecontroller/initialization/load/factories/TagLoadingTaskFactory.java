package kpn.financecontroller.initialization.load.factories;

import kpn.financecontroller.initialization.load.calculators.PathCalculator;
import kpn.financecontroller.initialization.load.creators.CollectorCreator;
import kpn.financecontroller.initialization.entities.TagInitialEntity;
import kpn.financecontroller.initialization.load.tasks.LoadingTaskImpl;
import kpn.financecontroller.initialization.load.tasks.LoadingTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class TagLoadingTaskFactory implements LoadingTaskFactory<Long, TagInitialEntity> {

    private static final String PATH = "tags.json";
    private static final String ID = "TAGS";

    private final PathCalculator<String, String> pathCalculator;
    private final CollectorCreator<Long, TagInitialEntity> collectorCreator;

    @Autowired
    public TagLoadingTaskFactory(PathCalculator<String, String> pathCalculator, CollectorCreator<Long, TagInitialEntity> collectorCreator) {
        this.pathCalculator = pathCalculator;
        this.collectorCreator = collectorCreator;
    }

    @Override
    public LoadingTask<Long, TagInitialEntity> create() {
        return new LoadingTaskImpl<>(pathCalculator, collectorCreator, PATH, ID);
    }
}
