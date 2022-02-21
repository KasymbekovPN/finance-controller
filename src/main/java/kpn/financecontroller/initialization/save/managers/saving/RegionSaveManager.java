package kpn.financecontroller.initialization.save.managers.saving;

import kpn.financecontroller.builders.Builder;
import kpn.financecontroller.checkers.GroupChecker;
import kpn.financecontroller.converters.Converter;
import kpn.financecontroller.data.domains.region.Region;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.collectors.LoadDataCollector;
import kpn.financecontroller.initialization.entities.CountryInitialEntity;
import kpn.financecontroller.initialization.entities.RegionInitialEntity;
import kpn.financecontroller.initialization.save.updaters.CollectorUpdater;
import kpn.financecontroller.result.Result;
import lombok.Setter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Profile("dev")
public class RegionSaveManager extends AbstractSaveManager<Long, RegionInitialEntity> {

    private static final String ID = "REGIONS";

    private final DTOService<Region, RegionEntity, Long> dtoService;
    private final Converter<RegionInitialEntity, RegionEntity> converter;
    private final Builder<Object, String> concatBuilder;
    private final CollectorUpdater<Long, RegionInitialEntity> collectorUpdater;

    @Setter
    private LoadDataCollector<Long, RegionInitialEntity> regionCollector;
    @Setter
    private LoadDataCollector<Long, CountryInitialEntity> countryCollector;

    public RegionSaveManager(GroupChecker<LoadDataCollector<?, ?>> collectorChecker,
                             DTOService<Region, RegionEntity, Long> dtoService,
                             Converter<RegionInitialEntity, RegionEntity> converter,
                             Builder<Object, String> concatBuilder,
                             CollectorUpdater<Long, RegionInitialEntity> collectorUpdater) {
        super(ID, collectorChecker);
        this.dtoService = dtoService;
        this.converter = converter;
        this.concatBuilder = concatBuilder;
        this.collectorUpdater = collectorUpdater;
    }

    @Override
    protected Result<Void> checkCollectors() {
        return collectorChecker.reset()
                .set(ID, regionCollector)
                .set("COUNTRIES", countryCollector)
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
        Map<Long, RegionInitialEntity> entities = regionCollector.getEntities();
        for (Map.Entry<Long, RegionInitialEntity> entry : entities.entrySet()) {

            RegionInitialEntity regionInitialEntity = entry.getValue();
            Long countryId = regionInitialEntity.getCountryId();
            Optional<CountryInitialEntity> maybeCountry = countryCollector.getEntity(countryId);
            maybeCountry.ifPresent(countryInitialEntity -> regionInitialEntity.setCountryId(countryInitialEntity.getId()));

            RegionEntity regionEntity = converter.convert(regionInitialEntity);
            Result<Region> saveResult = dtoService.saver().save(regionEntity);
            if (saveResult.getSuccess()){
                Region region = saveResult.getValue();
                collectorUpdater.add(entry.getKey(), region.getId());
            } else {
                success = false;
                concatBuilder.append(entry.getKey());
            }
        }

        collectorUpdater.update(regionCollector);

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
        regionCollector = null;
        countryCollector = null;
    }
}
