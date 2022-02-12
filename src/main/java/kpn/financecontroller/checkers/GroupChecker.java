package kpn.financecontroller.checkers;

public interface GroupChecker<T> extends Checker{
    GroupChecker<T> reset();
    GroupChecker<T> set(String key, T value);
}
