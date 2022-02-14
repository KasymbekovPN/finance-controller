package kpn.financecontroller.initialization.load.creators;

import com.google.gson.Gson;
import kpn.financecontroller.initialization.collectors.LoadDataCollector;
import kpn.financecontroller.initialization.collectors.LoadDataCollectorImpl;
import kpn.financecontroller.initialization.entities.CountryInitialEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class CountryCollectorCreator extends AbstractCollectorCreator<Long, CountryInitialEntity> {

    public CountryCollectorCreator() {
        super("COUNTRY");
    }

    @Override
    protected LoadDataCollector<Long, CountryInitialEntity> createCollector(String source) {
        return new Gson().fromJson(source, CountryDataCollector.class);
    }

    private static class CountryDataCollector extends LoadDataCollectorImpl<Long, CountryInitialEntity>{}
}
