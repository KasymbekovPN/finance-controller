package kpn.financecontroller.initialization.tasks;

import kpn.taskexecutor.lib.contexts.Context;
import kpn.taskexecutor.lib.tasks.Task;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FileReadingTask implements Task {
    private final String notExistPath;

    private boolean continuationPossible;

    @Override
    public void execute(Context context) {
        Task.super.execute(context);
    }

    @Override
    public boolean isContinuationPossible() {
        return continuationPossible;
    }
}
