// TODO: 16.03.2022 del
//package kpn.financecontroller.initialization._old.old.load.factories;
//
//import kpn.financecontroller.initialization._old.old.load.calculators.PathCalculator;
//import kpn.financecontroller.initialization._old.old.load.creators.CollectorCreator;
//import kpn.financecontroller.initialization._old.old.load.tasks.LoadingTask;
//import kpn.financecontroller.initialization._old.old.load.tasks.LoadingTaskImpl;
//
//// TODO: 27.02.2022 del ???
//public class LoadingTaskFactoryImpl<K, E> implements LoadingTaskFactory<K, E> {
//
//    private final PathCalculator<String, String> pathCalculator;
//    private final CollectorCreator<K, E> collectorCreator;
//    private final String id;
//    private final String path;
//
//    public LoadingTaskFactoryImpl(PathCalculator<String, String> pathCalculator,
//                                  CollectorCreator<K, E> collectorCreator,
//                                  String id,
//                                  String path) {
//        this.pathCalculator = pathCalculator;
//        this.collectorCreator = collectorCreator;
//        this.id = id;
//        this.path = path;
//    }
//
//    @Override
//    public LoadingTask<K, E> create() {
//        return new LoadingTaskImpl<>(pathCalculator, collectorCreator, path, id);
//    }
//}
