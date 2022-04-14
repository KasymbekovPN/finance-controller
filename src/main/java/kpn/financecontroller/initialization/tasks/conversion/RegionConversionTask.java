package kpn.financecontroller.initialization.tasks.conversion;

import kpn.financecontroller.data.entities.country.CountryEntity;
import kpn.financecontroller.data.entities.region.RegionEntity;
import kpn.financecontroller.initialization.entities.RegionJsonEntity;
import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Entities;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.jsonObjs.RegionLongKeyJsonObj;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.storage.ObjectStorage;
import kpn.financecontroller.initialization.tasks.BaseTask;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import lombok.Setter;

import java.util.Optional;

final public class RegionConversionTask extends BaseTask {

    @Setter
    private Long entityId;

    @Override
    public void execute(Context context) {
        reset();
        ObjectStorage storage = getStorage(context);
        Result<RegionLongKeyJsonObj> result = createContextManager(context).get(key, Properties.JSON_OBJECT_CREATION_RESULT, RegionLongKeyJsonObj.class);
        if (result.isSuccess()){
            RegionLongKeyJsonObj jsonObj = result.getValue();
            Optional<RegionJsonEntity> maybeJsonEntity = jsonObj.getEntity(entityId);
            if (maybeJsonEntity.isPresent()){
                RegionJsonEntity jsonEntity = maybeJsonEntity.get();
                Optional<RegionEntity> maybeEntity = convert(jsonEntity, context);
                if (maybeEntity.isPresent()){
                    storage.put(jsonEntity.getId(), maybeEntity.get());
                    continuationPossible = true;
                } else {
                    calculateAndSetCode(key, Codes.ENTITY_CONVERSION_FAIL);
                }
            } else {
                calculateAndSetCode(key, Codes.ENTITY_NOT_EXIST_ON_CONVERSION);
            }
        } else {
            calculateAndSetCode(key, Codes.NO_JSON_OBJECT);
        }
        putResultIntoContext(context, Properties.JSON_TO_DB_CONVERSION_RESULT, storage);
    }

    private ObjectStorage getStorage(Context context) {
        ResultContextManager contextManager = createContextManager(context);
        Result<ObjectStorage> storageResult = contextManager.get(key, Properties.JSON_TO_DB_CONVERSION_RESULT, ObjectStorage.class);
        if (storageResult.isSuccess()){
            return storageResult.getValue();
        }

        ObjectStorage storage = new ObjectStorage();
        ImmutableResult<ObjectStorage> result = ImmutableResult.<ObjectStorage>ok(storage).build();
        contextManager.put(key, Properties.JSON_TO_DB_CONVERSION_RESULT, result);

        return storage;
    }

    // TODO: 11.04.2022 must return result
    private Optional<RegionEntity> convert(RegionJsonEntity value, Context context) {
        ResultContextManager manager = createContextManager(context);
        Result<ObjectStorage> result = manager.get(Entities.COUNTRIES, Properties.JSON_TO_DB_CONVERSION_RESULT, ObjectStorage.class);
        if (result.isSuccess() && result.getValue().containsKey(value.getCountryId())){
            RegionEntity entity = new RegionEntity();
            entity.setId(value.getId());
            entity.setName(value.getName());
            entity.setCountryEntity((CountryEntity) result.getValue().get(value.getCountryId()));

            return Optional.of(entity);
        }

        return Optional.empty();
    }
}
