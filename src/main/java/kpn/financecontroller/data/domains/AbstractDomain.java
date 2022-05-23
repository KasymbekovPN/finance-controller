package kpn.financecontroller.data.domains;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.util.Map;
import java.util.Queue;
import java.util.function.Function;

@MappedSuperclass
@Setter
@Getter
@EqualsAndHashCode
public abstract class AbstractDomain implements Domain {
    protected static final String DEFAULT_GETTING_RESULT = "-";
    protected Long id;

    @Override
    public String get(Queue<String> path) {
        String key = path.poll();
        if (key != null){
            Map<String, Function<GetterArg, String>> getters = takeGetters();
            if (getters.containsKey(key)){
                return getters.get(key).apply(new GetterArg(this, path));
            }
        }
        return DEFAULT_GETTING_RESULT;
    }

    abstract protected Map<String, Function<GetterArg, String>> takeGetters();

    @RequiredArgsConstructor
    @Getter
    protected static class GetterArg{
        private final AbstractDomain domain;
        private final Queue<String> path;
    }
}
