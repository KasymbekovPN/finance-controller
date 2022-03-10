package kpn.financecontroller.initialization.old.save.managers.saving;

import kpn.financecontroller.builders.Builder;
import kpn.financecontroller.checkers.GroupChecker;
import kpn.financecontroller.converters.Converter;
import kpn.financecontroller.data.domains.city.City;
import kpn.financecontroller.data.entities.city.CityEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.old.collectors.LoadDataCollector;
import kpn.financecontroller.initialization.old.entities.CityInitialEntity;
import kpn.financecontroller.initialization.old.entities.RegionInitialEntity;
import kpn.financecontroller.initialization.old.save.updaters.CollectorUpdater;
import kpn.financecontroller.result.Result;
import lombok.Setter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

// TODO: 27.02.2022 del ???
//@Service
//@Profile("dev")
public class CitySaveManager extends AbstractSaveManager<Long, CityInitialEntity> {

    private static final String ID = "CITIES";

    private final DTOService<City, CityEntity, Long> dtoService;
    private final Converter<CityInitialEntity, CityEntity> converter;
    private final Builder<Object, String> concatBuilder;
    private final CollectorUpdater<Long, CityInitialEntity> collectorUpdater;

    @Setter
    private LoadDataCollector<Long, CityInitialEntity> cityCollector;
    @Setter
    private LoadDataCollector<Long, RegionInitialEntity> regionCollector;

    public CitySaveManager(GroupChecker<LoadDataCollector<?, ?>> collectorChecker,
                           DTOService<City, CityEntity, Long> dtoService,
                           Converter<CityInitialEntity, CityEntity> converter,
                           Builder<Object, String> concatBuilder,
                           CollectorUpdater<Long, CityInitialEntity> collectorUpdater) {
        super(ID, collectorChecker);
        this.dtoService = dtoService;
        this.converter = converter;
        this.concatBuilder = concatBuilder;
        this.collectorUpdater = collectorUpdater;
    }

    @Override
    protected Result<Void> checkCollectors() {
        return collectorChecker.reset()
                .set(ID, cityCollector)
                .set("REGIONS", regionCollector)
                .check();
    }

    @Override
    protected Result<Void> deleteBefore() {
        return dtoService.deleter().all();
    }

    @Override
    protected Result<Void> saveImpl() {
        boolean success = true;
        collectorUpdater.reset();
        concatBuilder.reset();
        Map<Long, CityInitialEntity> entities = cityCollector.getEntities();
        for (Map.Entry<Long, CityInitialEntity> entry : entities.entrySet()) {

            // TODO: 19.02.2022 each iteration must call method of some bean
            CityInitialEntity cityInitialEntity = entry.getValue();
            Optional<RegionInitialEntity> maybeRegion = regionCollector.getEntity(cityInitialEntity.getRegionId());
            maybeRegion.ifPresent(regionInitialEntity -> cityInitialEntity.setRegionId(regionInitialEntity.getId()));

            CityEntity cityEntity = converter.convert(cityInitialEntity);
            Result<City> result = dtoService.saver().save(cityEntity);
            if (result.getSuccess()){
                City city = result.getValue();
                collectorUpdater.add(entry.getKey(), city.getId());
            } else {
                success = false;
                concatBuilder.append(entry.getKey());
            }
        }

        collectorUpdater.update(cityCollector);

        Result.Builder<Void> builder = Result.<Void>builder()
                .success(success)
                .code(success ? "save.manager.saving.success" : "save.manager.saving.fail")
                .arg(ID);
        if (!success){
            builder.arg(concatBuilder.build());
        }

        return builder.build();
    }

    @Override
    public void clearCollector() {
        cityCollector = null;
        regionCollector = null;
    }
}
