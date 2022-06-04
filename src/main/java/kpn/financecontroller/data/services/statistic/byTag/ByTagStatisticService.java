package kpn.financecontroller.data.services.statistic.byTag;

public interface ByTagStatisticService<TASK, RESULT> {
    RESULT calculate(TASK... tasks);
}
