package kpn.financecontroller.data.propertyExtractors.factory;

import kpn.financecontroller.data.propertyExtractors.tasks.Task;

public interface TaskFactory<K, E> {
    Task<K, E> create();
}
