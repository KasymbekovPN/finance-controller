// TODO: 16.03.2022 del
//package kpn.financecontroller.initialization._old.old.load.tasks;
//
//import kpn.financecontroller.initialization._old.old.load.calculators.PathCalculator;
//import kpn.financecontroller.initialization._old.old.load.creators.CollectorCreator;
//import kpn.financecontroller.initialization._old.old.collectors.LoadDataCollector;
//import kpn.financecontroller.result.Result;
//
//import java.util.Arrays;
//
//// TODO: 27.02.2022 del ???
//public class LoadingTaskImpl<K, E> implements LoadingTask<K, E> {
//    private final PathCalculator<String, String> pathCalculator;
//    private final CollectorCreator<K, E> collectorCreator;
//    private final String path;
//    private final String id;
//
//    private LoadDataCollector<K, E> collector;
//
//    public LoadingTaskImpl(PathCalculator<String, String> pathCalculator,
//                           CollectorCreator<K, E> collectorCreator,
//                           String path,
//                           String id) {
//        this.pathCalculator = pathCalculator;
//        this.collectorCreator = collectorCreator;
//        this.path = path;
//        this.id = id;
//    }
//
//    @Override
//    public Result<String> calculatePath() {
//        Result<String> result = pathCalculator.calculate(path);
//        Result.Builder<String> builder = Result.<String>builder()
//                .success(result.getSuccess())
//                .value(result.getValue())
//                .code(result.getCode())
//                .arg(id);
//        Arrays.stream(result.getArgs()).forEach(builder::arg);
//        return builder.build();
//    }
//
//    @Override
//    public Result<Void> fillCollector(String jsonContent) {
//        Result<LoadDataCollector<K, E>> result = collectorCreator.create(jsonContent);
//        collector = result.getValue();
//        return convertResult(result);
//    }
//
//    @Override
//    public LoadDataCollector<K, E> getCollector() {
//        return collector;
//    }
//
//    private Result<Void> convertResult(Result<?> inResult) {
//        Result.Builder<Void> builder = Result.<Void>builder()
//                .success(inResult.getSuccess())
//                .code(inResult.getCode());
//        Arrays.stream(inResult.getArgs()).forEach(builder::arg);
//        return builder.build();
//    }
//}
