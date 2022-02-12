package kpn.financecontroller.builders;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class ConcatBuilder implements Builder<Object, String> {

    private final String delimiter;

    private List<Object> items = new ArrayList<>();

    public ConcatBuilder() {
        this.delimiter = ", ";
    }

    public ConcatBuilder(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public Builder<Object, String> reset() {
        items = new ArrayList<>();
        return this;
    }

    @Override
    public Builder<Object, String> append(Object value) {
        items.add(value);
        return this;
    }

    @Override
    public String build() {
        StringBuilder sb = new StringBuilder();
        AtomicReference<String> d = new AtomicReference<>("");
        items.forEach(i -> {
            sb.append(d.get()).append(i);
            d.set(delimiter);
        });
        return sb.toString();
    }
}
