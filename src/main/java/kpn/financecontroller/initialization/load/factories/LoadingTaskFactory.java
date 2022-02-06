package kpn.financecontroller.initialization.load.factories;

import kpn.financecontroller.initialization.load.tasks.LoadingTask;

public interface LoadingTaskFactory<K, E> {
    LoadingTask<K, E> create();
}
