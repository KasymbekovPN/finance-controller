package kpn.financecontroller.initialization.tasks;

import kpn.financecontroller.initialization.generators.valued.Codes;
import kpn.financecontroller.initialization.generators.valued.Properties;
import kpn.financecontroller.initialization.generators.valued.Valued;
import kpn.financecontroller.initialization.generators.valued.ValuedGenerator;
import kpn.financecontroller.initialization.managers.context.ContextManager;
import kpn.financecontroller.result.Result;
import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.tasks.Task;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Function;

@RequiredArgsConstructor
public class FileReadingTask implements Task {
    private final Valued<String> key;
    private final String path;
    private final Function<Context, ContextManager> managerCreator;
    private final ValuedGenerator<String> valuedGenerator;

    private boolean continuationPossible;

    @Override
    public void execute(Context context) {
        continuationPossible = false;
        Result.Builder<String> builder = Result.<String>builder();
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)){
            if (inputStream != null){
                try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))){
                    StringBuilder stringBuilder = new StringBuilder();
                    reader.lines().forEach(stringBuilder::append);
                    builder.value(stringBuilder.toString());
                    continuationPossible = true;
                } catch (IOException ex){
                    builder.code(valuedGenerator.generate(key, Codes.FAIL_FILE_READING));
                    ex.printStackTrace();
                }
            } else {
                builder.code(valuedGenerator.generate(key, Codes.FAIL_FILE_READING));
            }
        } catch (IOException ex){
            builder.code(valuedGenerator.generate(key, Codes.FAIL_FILE_READING));
            ex.printStackTrace();
        }

        ContextManager contextManager = managerCreator.apply(context);
        contextManager.put(key, Properties.RESULT, builder.success(isContinuationPossible()).arg(key).build());
    }

    @Override
    public boolean isContinuationPossible() {
        return continuationPossible;
    }
}
