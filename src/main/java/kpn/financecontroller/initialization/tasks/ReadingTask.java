package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.managers.context.ResultContextManager;
import kpn.taskexecutor.lib.contexts.Context;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Function;

final public class ReadingTask extends BaseTask {

    @Setter
    private String path;

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
        String value = null;
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)){
            if (inputStream != null){
                try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))){
                    StringBuilder stringBuilder = new StringBuilder();
                    reader.lines().forEach(stringBuilder::append);
                    value = stringBuilder.toString();
                    continuationPossible = true;
                } catch (IOException ex){
                    calculateAndSetCode(key, Codes.FAIL_FILE_READING);
                    ex.printStackTrace();
                }
            } else {
                calculateAndSetCode(key, Codes.FAIL_FILE_READING);
            }
        } catch (IOException ex){
            calculateAndSetCode(key, Codes.FAIL_FILE_READING);
            ex.printStackTrace();
        }

        putResultIntoContext(context, Properties.FILE_READING_RESULT, value);
    }
}