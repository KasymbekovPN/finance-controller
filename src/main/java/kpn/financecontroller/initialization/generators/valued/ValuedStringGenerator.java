package kpn.financecontroller.initialization.generators.valued;

public class ValuedStringGenerator implements ValuedGenerator<String> {
    @Override
    public String generate(Valued<String> v0, Valued<String> v1) {
        return v0.getValue() + "..." + v1.getValue();
    }
}
