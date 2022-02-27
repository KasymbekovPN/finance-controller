package kpn.financecontroller.initialization.task;

import kpn.financecontroller.initialization.context.Context;

public interface Task {
    void execute(Context context);
    boolean isContinuationPossible();
    String getKey();
}
