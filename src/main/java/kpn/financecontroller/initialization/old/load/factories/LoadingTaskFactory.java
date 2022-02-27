package kpn.financecontroller.initialization.old.load.factories;

import kpn.financecontroller.initialization.old.load.tasks.LoadingTask;

// TODO: 27.02.2022 del ???
public interface LoadingTaskFactory<K, E> {
    LoadingTask<K, E> create();
}
