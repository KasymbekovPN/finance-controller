package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.managers.context.ContextManager;
import kpn.taskexecutor.lib.contexts.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Function;

final public class FileReadingTask extends BaseTask {
    private final String path;

    public FileReadingTask(Valued<String> key,
                           ValuedGenerator<String> valuedGenerator,
                           Function<Context, ContextManager> managerCreator,
                           String path) {
        super(key, valuedGenerator, managerCreator);
        this.path = path;
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