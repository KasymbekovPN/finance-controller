package kpn.financecontroller.data.services.statistic.byTag;

public interface ByTagStatisticService<T, R> {
    R calculate(T... tasks);
}
