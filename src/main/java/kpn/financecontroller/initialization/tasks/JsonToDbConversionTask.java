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

import java.util.Map;
import java.util.function.Function;

final public class JsonToDbConversionTask extends BaseTask{

    public JsonToDbConversionTask(Valued<String> key,
                                  ValuedGenerator<String> valuedGenerator,
                                  Function<Context, ResultContextManager> managerCreator) {
        super(key, valuedGenerator, managerCreator);
    }

    @Override
    public void execute(Context context) {
        super.execute(context);
        TagStorage tagStorage = null;
        Result<TagLongKeyJsonObj> result = createContextManager(context).get(key, Properties.JSON_OBJECT_CREATION_RESULT, TagLongKeyJsonObj.class);
        if (result.getSuccess()){
            tagStorage = new TagStorage();
            TagLongKeyJsonObj tagLongKeyJsonObj = result.getValue();
            for (Map.Entry<Long, TagJsonEntity> entry : tagLongKeyJsonObj.getEntities().entrySet()) {
                tagStorage.put(entry.getKey(), convert(entry.getValue()));
            }
            continuationPossible = true;
        } else {
            calculateAndSetCode(key, Codes.NO_JSON_OBJECT);
        }
        putResultIntoContext(context, Properties.JSON_TO_DB_CONVERSION_RESULT, tagStorage);
    }

    private TagEntity convert(TagJsonEntity value) {
        TagEntity tagEntity = new TagEntity();
        tagEntity.setId(value.getId());
        tagEntity.setName(value.getName());

        return tagEntity;
    }
}
