package kpn.financecontroller.data.propertyExtractors.manager;

import kpn.financecontroller.data.propertyExtractors.ResourceFileReader;
import kpn.financecontroller.data.propertyExtractors.tasks.Task;
import kpn.financecontroller.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Profile("dev")
public class TaskManagerImpl implements TaskManager {

    private final ResourceFileReader reader;

    @Autowired
    public TaskManagerImpl(ResourceFileReader reader) {
        this.reader = reader;
    }

    @Override
    public Result<Void> execute(Task<?, ?> task) {
        Result<Void> result;
        Result<String> sResult = task.calculatePath();
        if (sResult.getSuccess()){
            sResult = read(sResult.getValue());
            result = sResult.getSuccess()
                ? task.fillCollector(sResult.getValue())
                : convertResult(sResult);
        } else {
            result = convertResult(sResult);
        }

        task.setResult(result);
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
