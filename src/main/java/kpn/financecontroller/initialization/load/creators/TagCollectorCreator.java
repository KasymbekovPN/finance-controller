package kpn.financecontroller.initialization.load.creators;

import com.google.gson.Gson;
import kpn.financecontroller.initialization.collectors.LoadDataCollector;
import kpn.financecontroller.initialization.collectors.LoadDataCollectorImpl;
import kpn.financecontroller.initialization.entities.TagInitialEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class TagCollectorCreator extends AbstractCollectorCreator<Long, TagInitialEntity> {

    public TagCollectorCreator() {
        super("TAGS");
    }

    @Override
    protected LoadDataCollector<Long, TagInitialEntity> createCollector(String source) {
        return new Gson().fromJson(source, TagLoadDataCollector.class);
    }

    private static class TagLoadDataCollector extends LoadDataCollectorImpl<Long, TagInitialEntity> {}
}
