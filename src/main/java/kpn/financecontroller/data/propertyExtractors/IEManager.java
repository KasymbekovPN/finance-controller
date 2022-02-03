package kpn.financecontroller.data.propertyExtractors;

import kpn.financecontroller.result.Result;

public interface IEManager<K, E> {
    IEManager<K, E> directory(String directory);
    IEManager<K, E> run();
    Result<Void> get();
}
