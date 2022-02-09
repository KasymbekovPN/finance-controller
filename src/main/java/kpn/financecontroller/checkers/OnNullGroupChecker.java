package kpn.financecontroller.checkers;

import kpn.financecontroller.result.Result;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class OnNullGroupChecker<T> implements GroupChecker<T> {
    private final String id;

    private final Map<String, T> collection = new HashMap<>();

    @Override
    public GroupChecker<T> reset() {
        collection.clear();
        return this;
    }

    @Override
    public GroupChecker<T> set(String key, T value) {
        collection.put(key, value);
        return this;
    }

    @Override
    public Result<Void> check() {
        Result.Builder<Void> builder = Result.<Void>builder();
        if (collection.isEmpty()){
            builder.success(true).code("groupChecker.collection.empty");
        } else {
            String status = findNull();
            if (status.isEmpty()){
                builder.success(true).code("groupChecker.checking.success");
            } else {
                builder.success(false).code("groupChecker.items.null").arg(status);
            }
        }

        reset();
        return builder.arg(id).build();
    }

    private String findNull(){
        StringBuilder line = new StringBuilder();
        String delimiter = "";
        for (Map.Entry<String, T> entry : collection.entrySet()) {
            if (entry.getValue() == null){
                line.append(delimiter).append(entry.getKey());
                delimiter = ", ";
            }
        }

        return line.toString();
    }
}
