package kpn.financecontroller.data.propertyExtractors;

import kpn.financecontroller.result.Result;

public interface IEManager<K, E> {
    void setDirectory(String directory);
    Result<Void> run();
}
