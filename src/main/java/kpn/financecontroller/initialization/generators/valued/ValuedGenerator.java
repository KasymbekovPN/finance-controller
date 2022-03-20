package kpn.financecontroller.initialization.generators.valued;

public interface ValuedGenerator<T> {
    T generate(Valued<T>... values);
}
