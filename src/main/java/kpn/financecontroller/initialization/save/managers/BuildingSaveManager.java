package kpn.financecontroller.initialization.save.managers;

import kpn.financecontroller.builders.Builder;
import kpn.financecontroller.checkers.GroupChecker;
import kpn.financecontroller.converters.Converter;
import kpn.financecontroller.data.domains.address.Address;
import kpn.financecontroller.data.entities.address.AddressEntity;
import kpn.financecontroller.data.services.DTOService;
import kpn.financecontroller.initialization.collectors.LoadDataCollector;
import kpn.financecontroller.initialization.entities.BuildingInitialEntity;
import kpn.financecontroller.initialization.entities.StreetInitialEntity;
import kpn.financecontroller.initialization.save.updaters.CollectorUpdater;
import kpn.financecontroller.result.Result;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Profile("dev")
public class BuildingSaveManager extends AbstractSaveManager<Long, BuildingInitialEntity> {

    private static final String ID = "BUILDINGS";

    private final DTOService<Address, AddressEntity, Long> dtoService;
    private final Converter<BuildingInitialEntity, AddressEntity> converter;
    private final Builder<Object, String> concatBuilder;
    private final CollectorUpdater<Long, BuildingInitialEntity> collectorUpdater;

    @Setter
    private LoadDataCollector<Long, BuildingInitialEntity> buildingCollector;
    @Setter
    private LoadDataCollector<Long, StreetInitialEntity> streetCollector;

    @Autowired
    public BuildingSaveManager(GroupChecker<LoadDataCollector<?, ?>> collectorChecker,
                               DTOService<Address, AddressEntity, Long> dtoService,
                               Converter<BuildingInitialEntity, AddressEntity> converter,
                               Builder<Object, String> concatBuilder,
                               CollectorUpdater<Long, BuildingInitialEntity> collectorUpdater) {
        super(ID, collectorChecker);
        this.dtoService = dtoService;
        this.converter = converter;
        this.concatBuilder = concatBuilder;
        this.collectorUpdater = collectorUpdater;
    }

    @Override
    protected Result<Void> checkCollectors() {
        return collectorChecker.reset()
                .set(ID, buildingCollector)
                .set("STREETS", streetCollector)
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
        Map<Long, BuildingInitialEntity> entities = buildingCollector.getEntities();
        for (Map.Entry<Long, BuildingInitialEntity> entry : entities.entrySet()) {
            BuildingInitialEntity buildingInitialEntity = entry.getValue();
            Optional<StreetInitialEntity> maybeStreet = streetCollector.getEntity(buildingInitialEntity.getStreetId());
            maybeStreet.ifPresent(streetInitialEntity -> buildingInitialEntity.setStreetId(streetInitialEntity.getId()));

            AddressEntity addressEntity = converter.convert(buildingInitialEntity);
            Result<Address> result = dtoService.saver().save(addressEntity);
            if (result.getSuccess()){
                collectorUpdater.add(entry.getKey(), result.getValue().getId());
            } else {
                success = false;
                concatBuilder.append(entry.getKey());
            }
        }

        collectorUpdater.update(buildingCollector);
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
        buildingCollector = null;
        streetCollector = null;
    }
}
