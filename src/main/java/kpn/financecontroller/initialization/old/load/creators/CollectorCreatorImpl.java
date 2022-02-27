package kpn.financecontroller.initialization.old.load.creators;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import kpn.financecontroller.initialization.old.collectors.LoadDataCollector;
import kpn.financecontroller.result.Result;

// TODO: 27.02.2022 del ???
public class CollectorCreatorImpl<K, E> implements CollectorCreator<K, E> {

    private final String id;
    private final Class<? extends LoadDataCollector<K, E>> type;

    protected Result<LoadDataCollector<K, E>> lastResult;

    public CollectorCreatorImpl(String id, Class<? extends LoadDataCollector<K, E>> type) {
        this.id = id;
        this.type = type;
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

    private LoadDataCollector<K,E> createCollector(String source){
        return new Gson().fromJson(source, type);
    }

    private Result<LoadDataCollector<K, E>> createDefaultLastResult() {
        return Result.<LoadDataCollector<K, E>>builder()
                .success(false)
                .code("creator.collector.result.null")
                .arg(id)
                .build();
    }
}
