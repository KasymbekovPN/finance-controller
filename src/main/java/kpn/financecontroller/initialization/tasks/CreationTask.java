package kpn.financecontroller.initialization.tasks;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import lombok.Setter;

import java.util.function.Function;

final public class CreationTask extends BaseTask {
    @Setter
    private Class<?> type;

    public void setKey(Valued<String> key){
        this.key = key;
    }

    public void setValuedGenerator(ValuedGenerator<String> valuedGenerator){
        this.valuedGenerator = valuedGenerator;
    }

    public void setManagerCreator(Function<Context, ResultContextManager> managerCreator){
        this.managerCreator = managerCreator;
    }

    @Override
    public void execute(Context context) {
        super.execute(context);
        Object value = null;
        ResultContextManager contextManager = createContextManager(context);
        Result<String> fileReadingResult = contextManager.get(key, Properties.FILE_READING_RESULT, String.class);
        if (fileReadingResult.getSuccess()){
            try{
                value = new Gson().fromJson(fileReadingResult.getValue(), type);
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