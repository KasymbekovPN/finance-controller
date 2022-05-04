package kpn.financecontroller.data.services.statistic.byTag;

public interface ByTagStatisticService<Q, R> {
    R calculate(Q query);
}
