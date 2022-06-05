package kpn.financecontroller.rfunc.checker.size;

public interface SizeChecker<TYPE> {
    Boolean check(TYPE value, int size);
}
