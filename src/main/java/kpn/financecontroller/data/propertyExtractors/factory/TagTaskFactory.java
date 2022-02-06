package kpn.financecontroller.data.propertyExtractors.factory;

import kpn.financecontroller.data.propertyExtractors.calculators.PathCalculator;
import kpn.financecontroller.data.propertyExtractors.creator.CollectorCreator;
import kpn.financecontroller.data.propertyExtractors.entities.TagIE;
import kpn.financecontroller.data.propertyExtractors.tasks.CollectorTask;
import kpn.financecontroller.data.propertyExtractors.tasks.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class TagTaskFactory implements TaskFactory<Long, TagIE> {

    private static final String PATH = "tags.json";
    private static final String ID = "TAGS";

    private final PathCalculator<String, String> pathCalculator;
    private final CollectorCreator<Long, TagIE> collectorCreator;

    @Autowired
    public TagTaskFactory(PathCalculator<String, String> pathCalculator, CollectorCreator<Long, TagIE> collectorCreator) {
        this.pathCalculator = pathCalculator;
        this.collectorCreator = collectorCreator;
    }

    @Override
    public Task<Long, TagIE> create() {
        return new CollectorTask<>(pathCalculator, collectorCreator, PATH, ID);
    }
}
