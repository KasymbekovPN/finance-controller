package kpn.financecontroller.initialization.old.save.managers;

import kpn.financecontroller.builders.Builder;
import kpn.financecontroller.checkers.GroupChecker;
import kpn.financecontroller.converters.Converter;
import kpn.financecontroller.data.domains.street.Street;
import kpn.financecontroller.data.entities.street.StreetEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.old.collectors.LoadDataCollector;
import kpn.financecontroller.initialization.old.entities.CityInitialEntity;
import kpn.financecontroller.initialization.old.entities.StreetInitialEntity;
import kpn.financecontroller.initialization.old.save.managers.saving.AbstractSaveManager;
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
public class StreetSaveManager extends AbstractSaveManager<Long, StreetInitialEntity> {

    private static final String ID = "STREETS";

    private final DTOService<Street, StreetEntity, Long> dtoService;
    private final Converter<StreetInitialEntity, StreetEntity> converter;
    private final Builder<Object, String> concatBuilder;
    private final CollectorUpdater<Long, StreetInitialEntity> collectorUpdater;

    @Setter
    private LoadDataCollector<Long, StreetInitialEntity> streetCollector;
    @Setter
    private LoadDataCollector<Long, CityInitialEntity> cityCollector;

    public StreetSaveManager(GroupChecker<LoadDataCollector<?, ?>> collectorChecker,
                             DTOService<Street, StreetEntity, Long> dtoService,
                             Converter<StreetInitialEntity, StreetEntity> converter,
                             Builder<Object, String> concatBuilder,
                             CollectorUpdater<Long, StreetInitialEntity> collectorUpdater) {
        super(ID, collectorChecker);
        this.dtoService = dtoService;
        this.converter = converter;
        this.concatBuilder = concatBuilder;
        this.collectorUpdater = collectorUpdater;
    }

    @Override
    protected Result<Void> checkCollectors() {
        return collectorChecker.reset()
                .set(ID, streetCollector)
                .set("CITIES", cityCollector)
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
        Map<Long, StreetInitialEntity> entities = streetCollector.getEntities();
        for (Map.Entry<Long, StreetInitialEntity> entry : entities.entrySet()) {
            StreetInitialEntity streetInitialEntity = entry.getValue();
            Optional<CityInitialEntity> maybeCity = cityCollector.getEntity(streetInitialEntity.getCityId());
            maybeCity.ifPresent(cityInitialEntity -> streetInitialEntity.setCityId(cityInitialEntity.getId()));

            StreetEntity streetEntity = converter.convert(streetInitialEntity);
            Result<Street> result = dtoService.saver().save(streetEntity);
            if (result.getSuccess()){
                Street street = result.getValue();
                collectorUpdater.add(entry.getKey(), street.getId());
            } else {
                success = false;
                concatBuilder.append(entry.getKey());
            }
        }

        collectorUpdater.update(streetCollector);
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
        streetCollector = null;
        cityCollector = null;
    }
}
