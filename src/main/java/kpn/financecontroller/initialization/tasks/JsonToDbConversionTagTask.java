package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.data.entities.tag.TagEntity;
import kpn.financecontroller.initialization.entities.TagJsonEntity;
import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.jsonObjs.TagLongKeyJsonObj;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.initialization.storages.TagStorage;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.lib.contexts.Context;

import java.util.Optional;
import java.util.function.Function;

final public class JsonToDbConversionTagTask extends BaseTask{

    private final Long entityId;

    public JsonToDbConversionTagTask(Valued<String> key,
                                     ValuedGenerator<String> valuedGenerator,
                                     Function<Context, ResultContextManager> managerCreator,
                                     Long entityId) {
        super(key, valuedGenerator, managerCreator);
        this.entityId = entityId;
    }

    @Override
    public void execute(Context context) {
        super.execute(context);
        TagStorage tagStorage = getStorage(context);
        Result<TagLongKeyJsonObj> result = createContextManager(context).get(key, Properties.JSON_OBJECT_CREATION_RESULT, TagLongKeyJsonObj.class);
        if (result.getSuccess()){
            TagLongKeyJsonObj tagLongKeyJsonObj = result.getValue();
            Optional<TagJsonEntity> maybeEntity = tagLongKeyJsonObj.getEntity(entityId);
            if (maybeEntity.isPresent()){
                TagJsonEntity jsonEntity = maybeEntity.get();
                tagStorage.put(jsonEntity.getId(), convert(jsonEntity));
                continuationPossible = true;
            } else {
                calculateAndSetCode(key, Codes.ENTITY_NOT_EXIST_ON_CONVERSION);
            }
        } else {
            calculateAndSetCode(key, Codes.NO_JSON_OBJECT);
        }
        putResultIntoContext(context, Properties.JSON_TO_DB_CONVERSION_RESULT, tagStorage);
    }

    private TagStorage getStorage(Context context) {
        ResultContextManager contextManager = createContextManager(context);
        Result<TagStorage> storageResult = contextManager.get(key, Properties.JSON_TO_DB_CONVERSION_RESULT, TagStorage.class);
        if (storageResult.getSuccess()){
            return storageResult.getValue();
        }

        TagStorage tagStorage = new TagStorage();
        Result<TagStorage> result = Result.<TagStorage>builder()
                .success(true)
                .value(tagStorage)
                .build();
        contextManager.put(key, Properties.JSON_TO_DB_CONVERSION_RESULT, result);

        return tagStorage;
    }

    private TagEntity convert(TagJsonEntity value) {
        TagEntity tagEntity = new TagEntity();
        tagEntity.setId(value.getId());
        tagEntity.setName(value.getName());

        return tagEntity;
    }
}
