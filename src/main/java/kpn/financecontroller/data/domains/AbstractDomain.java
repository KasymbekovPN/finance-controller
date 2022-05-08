package kpn.financecontroller.data.domains;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.util.Queue;

@MappedSuperclass
@Setter
@Getter
public abstract class AbstractDomain implements Domain {
    protected static final String DEFAULT_GETTING_RESULT = "-";
    protected Long id;

    // TODO: 08.05.2022 del
    @Override
    public String get(Queue<String> path) {
        return DEFAULT_GETTING_RESULT;
    }
}
