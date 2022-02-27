package kpn.financecontroller.initialization.task;

import kpn.financecontroller.initialization.context.Context;
import kpn.financecontroller.result.Result;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

public class FileReadingTask implements Task {
    private static final String PATH_PROPERTY = "file.path";
    private static final String RESULT_PROPERTY = "file.reading.result";
    private static final String NO_PATH_CODE = "context.no.path";
    private static final String EXCEPTION_CODE = "file.reading.exception";
    private static final String STREAM_NULL_CODE = "stream.null.code";
    private static final String SUCCESS_CODE = "file.reading.success";

    @Getter
    private final String key;
    @Getter
    private boolean continuationPossible;

    public FileReadingTask(String key) {
        this.key = key;
    }

    @Override
    public void execute(Context context){
        continuationPossible = false;
        Result.Builder<String> builder = Result.<String>builder().arg(key);

        Optional<Object> maybePath = context.get(key, PATH_PROPERTY);
        if (maybePath.isPresent()){
            String path = String.valueOf(maybePath.get());
            try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)){
                if (inputStream != null){
                    try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))){
                        StringBuilder stringBuilder = new StringBuilder();
                        reader.lines().forEach(stringBuilder::append);
                        builder.value(stringBuilder.toString()).code(SUCCESS_CODE);
                        continuationPossible = true;
                    } catch (IOException ex){
                        builder.code(EXCEPTION_CODE);
                        ex.printStackTrace();
                    }
                } else {
                    builder.code(STREAM_NULL_CODE);
                }
            } catch (IOException ex){
                builder.code(EXCEPTION_CODE);
                ex.printStackTrace();
            }
        } else {
            builder.code(NO_PATH_CODE);
        }

        context.put(key, RESULT_PROPERTY, builder.success(continuationPossible).build());
    }
}
