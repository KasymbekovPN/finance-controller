package kpn.financecontroller.checkers;

// TODO: 27.02.2022 del ???
public interface GroupChecker<T> extends Checker{
    GroupChecker<T> reset();
    GroupChecker<T> set(String key, T value);
}
