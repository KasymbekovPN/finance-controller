package kpn.financecontroller.data.propertyExtractors.creator;

import com.google.gson.JsonSyntaxException;
import kpn.financecontroller.data.propertyExtractors.IECollector;
import kpn.financecontroller.result.Result;

abstract public class AbstractCollectorCreator<K, E> implements CollectorCreator<K, E> {

    protected final String id;

    protected Result<IECollector<K, E>> lastResult;

    public AbstractCollectorCreator(String id) {
        this.id = id;
    }

    @Override
    public Result<IECollector<K, E>> create(String source) {
        Result.Builder<IECollector<K, E>> builder = Result.<IECollector<K, E>>builder();
        try{
            IECollector<K, E> collector = createCollector(source);
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
    public Result<IECollector<K, E>> get() {
        if (lastResult == null){
            lastResult = createDefaultLastResult();
        }
        return lastResult;
    }

    protected abstract IECollector<K,E> createCollector(String source);

    private Result<IECollector<K, E>> createDefaultLastResult() {
        return Result.<IECollector<K, E>>builder()
                .success(false)
                .code("creator.collector.result.null")
                .arg(id)
                .build();
    }
}
