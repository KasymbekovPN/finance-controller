package kpn.financecontroller.builders;

// TODO: 27.02.2022 del ???
public interface Builder<R, T> {
    Builder<R, T> reset();
    Builder<R, T> append(R value);
    T build();
}
