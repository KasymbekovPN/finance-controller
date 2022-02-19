package kpn.financecontroller.initialization.load.creators;

import com.google.gson.Gson;
import kpn.financecontroller.initialization.collectors.LoadDataCollector;
import kpn.financecontroller.initialization.collectors.LoadDataCollectorImpl;
import kpn.financecontroller.initialization.entities.CityInitialEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class CityCollectorCreator extends AbstractCollectorCreator<Long, CityInitialEntity> {

    public CityCollectorCreator() {
        super("REGIONS");
    }

    @Override
    protected LoadDataCollector<Long, CityInitialEntity> createCollector(String source) {
        return new Gson().fromJson(source, CityLoadDataCollector.class);
    }

    private static class CityLoadDataCollector extends LoadDataCollectorImpl<Long, CityInitialEntity>{}
}
