package kpn.financecontroller.initialization.tasks.conversion;

import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.initialization.entities.TagJsonEntity;
import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.jsonObjs.TagLongKeyJsonObj;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.storage.ObjectStorage;
import kpn.financecontroller.initialization.tasks.BaseTask;
import kpn.lib.result.ImmutableResult;
import kpn.lib.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import lombok.Setter;

import java.util.Optional;

// TODO: 31.03.2022 generalize it
final public class TagConversionTask extends BaseTask {
    @Setter
    private Long entityId;

    @Override
    public void execute(Context context) {
        reset();
        ObjectStorage storage = createStorage(context);
        Result<TagLongKeyJsonObj> result = createContextManager(context).get(key, Properties.JSON_OBJECT_CREATION_RESULT, TagLongKeyJsonObj.class);
        if (result.isSuccess()){
            TagLongKeyJsonObj tagLongKeyJsonObj = result.getValue();
            Optional<TagJsonEntity> maybeEntity = tagLongKeyJsonObj.getEntity(entityId);
            if (maybeEntity.isPresent()){
                TagJsonEntity jsonEntity = maybeEntity.get();
                storage.put(jsonEntity.getId(), convert(jsonEntity));
                continuationPossible = true;
            } else {
                calculateAndSetCode(key, Codes.ENTITY_NOT_EXIST_ON_CONVERSION);
            }
        } else {
            calculateAndSetCode(key, Codes.NO_JSON_OBJECT);
        }
        putResultIntoContext(context, Properties.JSON_TO_DB_CONVERSION_RESULT, storage);
    }

    private ObjectStorage createStorage(Context context) {
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

    private TagEntity convert(TagJsonEntity value) {
        TagEntity tagEntity = new TagEntity();
        tagEntity.setId(value.getId());
        tagEntity.setName(value.getName());

        return tagEntity;
    }
}
