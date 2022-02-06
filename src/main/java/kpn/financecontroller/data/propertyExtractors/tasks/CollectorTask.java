package kpn.financecontroller.data.propertyExtractors.tasks;

import kpn.financecontroller.data.propertyExtractors.IECollector;
import kpn.financecontroller.data.propertyExtractors.calculators.PathCalculator;
import kpn.financecontroller.data.propertyExtractors.creator.CollectorCreator;
import kpn.financecontroller.result.Result;

import java.util.Arrays;

public class CollectorTask<K, E> implements Task<K, E> {
    private final PathCalculator<String, String> pathCalculator;
    private final CollectorCreator<K, E> collectorCreator;
    private final String path;
    private final String id;

    private IECollector<K, E> collector;

    public CollectorTask(PathCalculator<String, String> pathCalculator,
                         CollectorCreator<K, E> collectorCreator,
                         String path,
                         String id) {
        this.pathCalculator = pathCalculator;
        this.collectorCreator = collectorCreator;
        this.path = path;
        this.id = id;
    }

    @Override
    public Result<String> calculatePath() {
        Result<String> result = pathCalculator.calculate(path);
        Result.Builder<String> builder = Result.<String>builder()
                .success(result.getSuccess())
                .value(result.getValue())
                .code(result.getCode())
                .arg(id);
        Arrays.stream(result.getArgs()).forEach(builder::arg);
        return builder.build();
    }

    @Override
    public Result<Void> fillCollector(String jsonContent) {
        Result<IECollector<K, E>> result = collectorCreator.create(jsonContent);
        collector = result.getValue();
        return convertResult(result);
    }

    @Override
    public IECollector<K, E> getCollector() {
        return collector;
    }

    private Result<Void> convertResult(Result<?> inResult) {
        Result.Builder<Void> builder = Result.<Void>builder()
                .success(inResult.getSuccess())
                .code(inResult.getCode());
        Arrays.stream(inResult.getArgs()).forEach(builder::arg);
        return builder.build();
    }
}
