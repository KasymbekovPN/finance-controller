package kpn.financecontroller.initialization.generators.valued;

public interface ValuedGenerator<T> {
    T generate(Valued<T> v0, Valued<T> v1);
}
