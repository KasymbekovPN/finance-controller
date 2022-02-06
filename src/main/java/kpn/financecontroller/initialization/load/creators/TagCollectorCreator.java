package kpn.financecontroller.initialization.load.creators;

import com.google.gson.Gson;
import kpn.financecontroller.initialization.load.collectors.LoadDataCollector;
import kpn.financecontroller.initialization.load.collectors.LoadDataCollectorImpl;
import kpn.financecontroller.initialization.load.entities.TagLoadEntity;
import org.springframework.stereotype.Component;

@Component
public class TagCollectorCreator extends AbstractCollectorCreator<Long, TagLoadEntity> {

    public TagCollectorCreator() {
        super("TAGS");
    }

    @Override
    protected LoadDataCollector<Long, TagLoadEntity> createCollector(String source) {
        return new Gson().fromJson(source, TagLoadDataCollector.class);
    }

    private static class TagLoadDataCollector extends LoadDataCollectorImpl<Long, TagLoadEntity> {}
}
