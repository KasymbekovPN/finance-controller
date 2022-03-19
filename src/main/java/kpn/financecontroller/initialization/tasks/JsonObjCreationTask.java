package kpn.financecontroller.initialization.tasks;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.managers.context.ContextManager;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.lib.contexts.Context;

import java.util.function.Function;

final public class JsonObjCreationTask extends BaseTask{

    private final Class<?> classType;

    public JsonObjCreationTask(Valued<String> key,
                               ValuedGenerator<String> valuedGenerator,
                               Function<Context, ContextManager> managerCreator,
                               Class<?> classType) {
        super(key, valuedGenerator, managerCreator);
        this.classType = classType;
    }

    @Override
    public void execute(Context context) {
        super.execute(context);
        Object value = null;
        ContextManager contextManager = createContextManager(context);
        Result<Object> result = contextManager.get(key, Properties.FILE_READING_RESULT);
        if (result.getSuccess()){
            Result<String> sourceResult = (Result<String>) result.getValue();
            try{
                value = new Gson().fromJson(sourceResult.getValue(), classType);
                continuationPossible = true;
            } catch (JsonSyntaxException ex){
                calculateAndSetCode(key, Codes.JSON_SYNTAX_EXCEPTION);
            }
        } else {
            calculateAndSetCode(key, Codes.NO_STRING_CONTENT);
        }

        putResultIntoContext(context, Properties.JSON_OBJECT_CREATION_RESULT, value);
    }
}