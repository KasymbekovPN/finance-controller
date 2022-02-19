package kpn.financecontroller.initialization.load.creators;

import com.google.gson.Gson;
import kpn.financecontroller.initialization.collectors.LoadDataCollector;
import kpn.financecontroller.initialization.collectors.LoadDataCollectorImpl;
import kpn.financecontroller.initialization.entities.RegionInitialEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class RegionCollectorCreator extends AbstractCollectorCreator<Long, RegionInitialEntity>{

    public RegionCollectorCreator() {
        super("REGIONS");
    }

    @Override
    protected LoadDataCollector<Long, RegionInitialEntity> createCollector(String source) {
        return new Gson().fromJson(source, RegionLoadDataCollector.class);
    }

    private static class RegionLoadDataCollector extends LoadDataCollectorImpl<Long, RegionInitialEntity>{}
}
