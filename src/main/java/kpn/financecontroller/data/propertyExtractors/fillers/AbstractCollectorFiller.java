package kpn.financecontroller.data.propertyExtractors.fillers;

import com.google.gson.JsonSyntaxException;
import kpn.financecontroller.data.propertyExtractors.IECollector;
import kpn.financecontroller.result.Result;
import lombok.Getter;
import lombok.Setter;

abstract public class AbstractCollectorFiller<K, E> implements CollectorFiller<K, E> {

    private final String name;

    private IECollector<K, E> collector;

    public AbstractCollectorFiller(String name) {
        this.name = name;
    }

    @Override
    public Result<Void> fill(String jsonContent) {
        Result.Builder<Void> builder = Result.<Void>builder()
                .arg(name);
        try{
           collector = createCollector(jsonContent);
           builder
                   .success(true)
                   .code("json.parsing.success");
        } catch (JsonSyntaxException ex){
            ex.printStackTrace();
            builder
                    .success(false)
                    .code("json.parsing.fail");
        }
        return builder.build();
    }

    protected abstract IECollector<K,E> createCollector(String jsonContent);

    @Override
    public IECollector<K, E> get() {
        return collector;
    }

    @Getter
    @Setter
    protected static class IEAbstractEntity<K>{
        protected K id;
        protected Boolean possibility;
    }
}
