package kpn.financecontroller.initialization.task;

import kpn.financecontroller.data.entities.AbstractEntity;
import kpn.financecontroller.initialization.collector.LongKeyInitialEntityCollector;
import kpn.financecontroller.initialization.context.Context;
import kpn.financecontroller.initialization.converter.EntityConverter;
import kpn.financecontroller.initialization.entities.AbstractInitialEntity;
import kpn.financecontroller.result.Result;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class EntityConversionTask<IE extends AbstractInitialEntity, E extends AbstractEntity> implements Task{
    @Getter
    private final String key;
    private final String initialCollectorProperty;
    private final EntityConverter<IE, E> converter;

    @Getter
    private boolean continuationPossible;
    private LongKeyInitialEntityCollector<IE> collector;


    @Override
    public void execute(Context context) {
        if (checkCollectorOrSetResult(context)){
            HashMap<Long, E> resultMap = new HashMap<>();
            Map<Long, IE> entities = collector.getEntities();
            for (Map.Entry<Long, IE> entry : entities.entrySet()) {
                resultMap.put(entry.getKey(), converter.convert(entry.getValue()));
            }
            Result<Map<Long, E>> result = Result.<Map<Long, E>>builder()
                    .success(true)
                    .value(resultMap)
                    .arg(key)
                    .build();
            context.put(key, Property.RESULT.getValue(), result);
            continuationPossible = true;
        }
    }

    private boolean checkCollectorOrSetResult(Context context) {
        Optional<Object> maybeCollector = context.get(key, initialCollectorProperty);
        if (maybeCollector.isEmpty()){
            Result<Map<Long, E>> result = Result.<Map<Long, E>>builder()
                    .success(false)
                    .code(Codes.NO_COLLECTOR.getValue())
                    .arg(key)
                    .build();
            context.put(key, Property.RESULT.getValue(), result);
            return false;
        }

        collector = (LongKeyInitialEntityCollector<IE>) maybeCollector.get();
        return true;
    }

    @RequiredArgsConstructor
    @Getter
    public enum Property{
        RESULT("task.entityConversion.property.result");

        private final String value;
    }

    @RequiredArgsConstructor
    @Getter
    public enum Codes{
        NO_COLLECTOR("task.entityConversion.code.noCollector");

        private final String value;
    }
}
