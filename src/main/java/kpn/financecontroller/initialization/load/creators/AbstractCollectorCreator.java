package kpn.financecontroller.initialization.load.creators;

import com.google.gson.JsonSyntaxException;
import kpn.financecontroller.initialization.collectors.LoadDataCollector;
import kpn.financecontroller.result.Result;

abstract public class AbstractCollectorCreator<K, E> implements CollectorCreator<K, E> {

    protected final String id;

    protected Result<LoadDataCollector<K, E>> lastResult;

    public AbstractCollectorCreator(String id) {
        this.id = id;
    }

    @Override
    public Result<LoadDataCollector<K, E>> create(String source) {
        Result.Builder<LoadDataCollector<K, E>> builder = Result.<LoadDataCollector<K, E>>builder();
        try{
            LoadDataCollector<K, E> collector = createCollector(source);
            builder
                    .success(true)
                    .value(collector)
                    .code("creator.collector.parsing.success");
        } catch (JsonSyntaxException ex){
            ex.printStackTrace();
            builder
                    .success(false)
                    .code("creator.collector.parsing.fail");
        }
        lastResult = builder.arg(id).build();
        return lastResult;
    }

    @Override
    public Result<LoadDataCollector<K, E>> get() {
        if (lastResult == null){
            lastResult = createDefaultLastResult();
        }
        return lastResult;
    }

    protected abstract LoadDataCollector<K,E> createCollector(String source);

    private Result<LoadDataCollector<K, E>> createDefaultLastResult() {
        return Result.<LoadDataCollector<K, E>>builder()
                .success(false)
                .code("creator.collector.result.null")
                .arg(id)
                .build();
    }
}
