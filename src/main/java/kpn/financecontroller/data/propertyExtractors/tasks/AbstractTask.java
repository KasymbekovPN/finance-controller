package kpn.financecontroller.data.propertyExtractors.tasks;

import kpn.financecontroller.data.propertyExtractors.calculators.PathCalculator;
import kpn.financecontroller.data.propertyExtractors.creator.CollectorCreator;

abstract public class AbstractTask<K, E> implements Task<K, E> {
    private final PathCalculator<String, String> pathCalculator;
    private final CollectorCreator<K, E> collectorCreator;
    private final String id;

    public AbstractTask(PathCalculator<String, String> pathCalculator, CollectorCreator<K, E> collectorCreator, String id) {
        this.pathCalculator = pathCalculator;
        this.collectorCreator = collectorCreator;
        this.id = id;
    }
}
