package kpn.financecontroller.initialization.load.factories;

import kpn.financecontroller.initialization.load.calculators.PathCalculator;
import kpn.financecontroller.initialization.load.creators.CollectorCreator;
import kpn.financecontroller.initialization.entities.TagInitialEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class TagLoadingTaskFactory extends AbstractLoadingTaskFactory<Long, TagInitialEntity> {

    private static final String PATH = "tags.json";
    private static final String ID = "TAGS";

    @Autowired
    public TagLoadingTaskFactory(PathCalculator<String, String> pathCalculator,
                                 CollectorCreator<Long, TagInitialEntity> collectorCreator) {
        super(pathCalculator, collectorCreator, ID, PATH);
    }
}
