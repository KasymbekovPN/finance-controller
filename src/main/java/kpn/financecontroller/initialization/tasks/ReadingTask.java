package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.taskexecutor.lib.contexts.Context;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

final public class ReadingTask extends BaseTask {

    @Setter
    private String path;

    @Override
    public void execute(Context context) {
        reset();
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