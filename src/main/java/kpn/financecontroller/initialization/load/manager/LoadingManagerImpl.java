package kpn.financecontroller.initialization.load.manager;

import kpn.financecontroller.initialization.load.reader.ResourceFileReader;
import kpn.financecontroller.initialization.load.tasks.LoadingTask;
import kpn.financecontroller.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Profile("dev")
public class LoadingManagerImpl implements LoadingManager {

    private final ResourceFileReader reader;

    @Autowired
    public LoadingManagerImpl(ResourceFileReader reader) {
        this.reader = reader;
    }

    @Override
    public Result<Void> execute(LoadingTask<?, ?> loadingTask) {
        Result<Void> result;
        Result<String> sResult = loadingTask.calculatePath();
        if (sResult.getSuccess()){
            sResult = read(sResult.getValue());
            result = sResult.getSuccess()
                ? loadingTask.fillCollector(sResult.getValue())
                : convertResult(sResult);
        } else {
            result = convertResult(sResult);
        }

        return result;
    }

    private Result<Void> convertResult(Result<String> inResult) {
        Result.Builder<Void> builder = Result.<Void>builder()
                .success(inResult.getSuccess())
                .code(inResult.getCode());
        Arrays.stream(inResult.getArgs()).forEach(builder::arg);

        return builder.build();
    }

    private Result<String> read(String path) {
        return reader.read(path);
    }
}
