package kpn.financecontroller.initialization.generators.valued;

import java.util.Arrays;

public class ValuedStringGenerator implements ValuedGenerator<String> {
    private static final String DELIMITER = "...";

    @SafeVarargs
    @Override
    public final String generate(Valued<String>... values) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(values).forEach(v -> {sb.append(sb.length() == 0 ? "" : DELIMITER).append(v.getValue());});
        return sb.toString();
    }
}
