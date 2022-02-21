package kpn.financecontroller.initialization.load.factories;

import kpn.financecontroller.initialization.load.calculators.PathCalculator;
import kpn.financecontroller.initialization.load.creators.CollectorCreator;
import kpn.financecontroller.initialization.load.tasks.LoadingTask;
import kpn.financecontroller.initialization.load.tasks.LoadingTaskImpl;

public class LoadingTaskFactoryImpl<K, E> implements LoadingTaskFactory<K, E> {

    private final PathCalculator<String, String> pathCalculator;
    private final CollectorCreator<K, E> collectorCreator;
    private final String id;
    private final String path;

    public LoadingTaskFactoryImpl(PathCalculator<String, String> pathCalculator,
                                  CollectorCreator<K, E> collectorCreator,
                                  String id,
                                  String path) {
        this.pathCalculator = pathCalculator;
        this.collectorCreator = collectorCreator;
        this.id = id;
        this.path = path;
    }

    @Override
    public LoadingTask<K, E> create() {
        return new LoadingTaskImpl<>(pathCalculator, collectorCreator, path, id);
    }
}
