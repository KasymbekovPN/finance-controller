package kpn.financecontroller.data.propertyExtractors.tasks;

import kpn.financecontroller.data.propertyExtractors.IECollector;
import kpn.financecontroller.result.Result;

public interface Task<K, E> {
    Result<String> calculatePath();
    Result<Void> fillCollector(String jsonContent);
    IECollector<K, E> getCollector();
}
