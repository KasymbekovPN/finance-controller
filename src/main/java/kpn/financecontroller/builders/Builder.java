package kpn.financecontroller.builders;

public interface Builder<R, T> {
    Builder<R, T> reset();
    Builder<R, T> append(R value);
    T build();
}
